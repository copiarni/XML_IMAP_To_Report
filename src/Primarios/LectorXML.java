/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primarios;


import DataItems.DataItem;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ElementosXML.CampoOrigen;
import ElementosXML.ElementoXML;
import ElementosXML.Folder;
import ElementosXML.RepoSASLibrary;
import java.util.ArrayList;
import org.w3c.dom.Element;

/**
 * Lector de documento XML
 * @author Innova-TSN
 */
public class LectorXML {  
    
    private static LectorXML lectorXML=null;
    File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder db;
    Document document;
    
    
    /*Construye una unica vez el objeto LectorXML*/
    private LectorXML(){        
    }
    
    /*Devuelve la instancia del objeto Singleton LectorXML*/
    public static LectorXML getInstance(){
        if (lectorXML==null){
            lectorXML=new LectorXML();
        }
        return lectorXML;
    }
    
    /*Lee el XML y busca elementos en el*/
    public void parseador(String archivo) throws ParserConfigurationException, SAXException, IOException{
       xmlFile = new File(archivo);
       dbFactory= DocumentBuilderFactory.newInstance();
       db= dbFactory.newDocumentBuilder();
       document= db.parse(xmlFile);
       document.getDocumentElement().normalize();       
       
       buscaRepoSASLibrary();
       buscaRelacionIds();
       buscaDataItems();
       Node nodeDirEstruc=document.getElementsByTagName("MapFolder").item(0);
       buscaDirectorios(nodeDirEstruc);
    }
    
    /*Busca en el documento, elementos con nombre RepoSASLibrary*/
    private void buscaRepoSASLibrary(){
        NodeList nodeRSList=document.getElementsByTagName("ReposSASLibrary");
        for (int i=0;i<nodeRSList.getLength();i++){
            Node node=nodeRSList.item(i);
            String name=node.getAttributes().getNamedItem("name").getNodeValue();
            String obj=node.getAttributes().getNamedItem("obj").getNodeValue();
            RepoSASLibrary r=new RepoSASLibrary(name,obj,node);
            XML_IMAP_To_Report.listaElementosXML.add(r);
        }
    }
    
    /** busca en el documento, la relacion de ids de elementos origen (x999) y
    *   de ids de elementos DataItem(a9999)
    */
    private void buscaRelacionIds(){
        NodeList nodeRIList=document.getElementsByTagName("DataSourceTable");
        for(int i=0;i<nodeRIList.getLength();i++){
            Node nodeDST=nodeRIList.item(i);
            if(nodeDST.getAttributes().getNamedItem("dataSourceColumnMappings")!=null){
                String parraf=nodeDST.getAttributes().getNamedItem("dataSourceColumnMappings").getNodeValue();
                String[] listaRelacs=parraf.split(";");
                for(String s:listaRelacs){
                    String dataItemId=s.split(",")[0];
                    String origenId=s.split(",")[1];
                    for(ElementoXML el:XML_IMAP_To_Report.listaElementosXML){
                        if (origenId.equals(el.getobj())){
                            ((CampoOrigen)el).setDataItemsAsociados(dataItemId);
                        }
                    }
                }
            }
        }
    }
    
    /*Busca en el documento, elementos con nombre DataItem*/
    private void buscaDataItems(){
        NodeList nodeDIList=document.getElementsByTagName("DataItem");
        for(int i=0;i<nodeDIList.getLength();i++){
            Node nodeDI=nodeDIList.item(i);
            DataItem dai=new DataItem(nodeDI);
            XML_IMAP_To_Report.listaDataItems.add(dai);
        }
    }
    
    public void buscaDirectorios(Node nodeDirEstruc){
        
        String folderName=nodeDirEstruc.getAttributes().getNamedItem("label").getNodeValue();
        String folderId=nodeDirEstruc.getAttributes().getNamedItem("lid").getNodeValue();
        ArrayList<Folder> directorios=XML_IMAP_To_Report.listaDirectorios;
        Folder folder1=null;
        
        if(!folderId.equals("Root")){
            String idFather=nodeDirEstruc.getParentNode().getParentNode().getAttributes().getNamedItem("lid").getNodeValue();
            folder1 =new Folder(folderName,folderId,idFather);
            /*Se añade el id del nodo hijo a la lista de hijos del nodo padre y se establece el valorJerarquico*/
            for(Folder f:directorios){
                if(f.getFolderId().equals(idFather)){
                    folder1.setValorJerarquia(f.getValorJerarquia()+1);
                    f.addSubfolder(folderId);
                }
            }
        }else{
            folder1 =new Folder(folderName,folderId);
            folder1.setValorJerarquia(0);
        }        
        directorios.add(folder1);
        
        /*Si existen subfolders*/        
        if(((Element)nodeDirEstruc).getElementsByTagName("Subfolders").getLength()>0){            
            /*Recoger lista de nodos del primer subfolder(el resto de subfolders son de una estructura interna)*/          
            NodeList subMapFolderList = ((Element)nodeDirEstruc).getElementsByTagName("Subfolders").item(0).getChildNodes();            
            
            //estructurarDirectorios(subMapFolderList.item(1));
            for(int i=0;i < subMapFolderList.getLength();i++){                
                /*Al recorrer la lista de Nodos, aparecen otros elementos tipo Text(3) aparte de los nodos MapFolder*/
                if(subMapFolderList.item(i).getNodeType()==1){
                    /*Se realiza llama recursiva al metodo para volver a buscar dentro de este nodo*/
                    Node subMapFolderNode=subMapFolderList.item(i);
                    buscaDirectorios(subMapFolderList.item(i));                
                }                
            }                       
        }
        /*Si existen dataItems*/
        if(nodeDirEstruc.getAttributes().getNamedItem("ref1")!=null){            
            directorios.get(directorios.size()-1).SetDataItems(nodeDirEstruc.getAttributes().getNamedItem("ref1").getNodeValue());
        }
        
        
    }
}
