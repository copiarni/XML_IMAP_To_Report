/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementosXML;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Primarios.XML_IMAP_To_Report;

/**
 * Objeto tipo libreria. Almacena objetos tipo ReposTable
 * @author Innova-TSN
 */
public class RepoSASLibrary extends ElementoXML{
    private Node nodoDatos;
    private ArrayList<ReposTable> listaReposTable;
    
    public RepoSASLibrary(){
        super();        
    }
    
    
    public RepoSASLibrary(String name, String obj, Node nodoDatos){
        super(name,obj);
        this.nodoDatos=nodoDatos;
        setListaReposTable();
        
    }
    
    /*Identifica y guarda en una lista los objetos tipo ReposTable que alberga*/
    public void setListaReposTable(){
        if(((Element)nodoDatos).getElementsByTagName("ReposTable")!=null){ 
            listaReposTable=new ArrayList<>();
            NodeList nl=((Element)nodoDatos).getElementsByTagName("ReposTable");
            for(int i=0;i<nl.getLength();i++){
                Node n=nl.item(i);
                String name=n.getAttributes().getNamedItem("name").getNodeValue();
                String obj=n.getAttributes().getNamedItem("obj").getNodeValue();
                ReposTable rt=new ReposTable(name, obj, n,super.name);
                listaReposTable.add(rt);
                XML_IMAP_To_Report.listaElementosXML.add(rt);
            }
        }
    }
    
    
}
