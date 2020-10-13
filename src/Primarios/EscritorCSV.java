/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primarios;

import DataItems.DataItem;
import ElementosXML.Folder;
import ElementosXML.Join;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Innova-TSN
 */
public class EscritorCSV {    
    
    static ArrayList<Folder> directorios;
    static ArrayList<Join> relaciones;
    static ArrayList<DataItem> dataItems;
    
    /*Genera un documento CSV con los campos del Dataitem*/
    public static void volcadoCSV(String rutaReportes)throws IOException{
        String salidaCSV=rutaReportes+"\\CamposImap.csv"; 
        directorios=XML_IMAP_To_Report.listaDirectorios;
        dataItems=XML_IMAP_To_Report.listaDataItems;
        FileWriter fW=new FileWriter(salidaCSV);
        PrintWriter pW = new PrintWriter(fW);
        /*Cabecera*/
        pW.println("NOMBRE;FORMATO;EXPRESION;DESCRIPCION");
        for(Folder f:directorios){
            String direct=f.getFolderName();
            if(f.getQtyDataItems()>0){
                ArrayList<String> daitIds=f.getDataItems();
                /*Por cada DataItemiD que almacene el directorio*/
                for(String id : daitIds){
                    /*Por cada DataItem del Imap*/
                    for(DataItem dait: dataItems){
                        if(dait.getObj().equals(id)){
                            pW.println(dait.getLabel()+";"+dait.getFmt()+";"+dait.getReference()+";"+dait.getDesc());                            
                        }                        
                    }
                }
            }
        }     
        pW.close(); 
        System.out.println(rutaReportes+"\\CamposImap.csv Generado correctamente");
    }  
    
    
    /*Genera un documento txt con la lista de dataitems*/
    public static void volcadoTXT(String rutaReportes)throws IOException{
       String reporteCompleto=rutaReportes+"\\EstructuraImap.txt";
       FileWriter fileWriter= new FileWriter(reporteCompleto);
       PrintWriter printWriter= new PrintWriter(fileWriter);
       
       printWriter.println("Localizados "+directorios.size()+" directorios en IMAP");
       printWriter.println("Numero de DataItems: "+dataItems.size());
       /*Por cada uno de los directorios*/
       for(Folder f:directorios){
           String tabu="";
           /*por cada nivel Jerarquico del directorio*/
           for(int i=0;i<f.getValorJerarquia();i++){
               tabu+="\t";
           }
           printWriter.println(tabu+f.getFolderName()+" (Directorio)");
           
           if(f.getQtyDataItems()>0){
            ArrayList<String> daitIds=f.getDataItems();
                /*Por cada DataItemiD que almacene el directorio*/
                for(String id : daitIds){
                    /*Por cada DataItem del Imap*/
                    for(DataItem dait: dataItems){
                        if(dait.getObj().equals(id)){
                            printWriter.println(tabu+"\t"+dait.getLabel());
                        }                        
                    }
                }
           }           
           
           
       }
       printWriter.close();
       System.out.println(rutaReportes+"\\EstructuraImap.txt Generado correctamente");
   }
    
    /*Genera un documento CSV con las relaciones de Imap*/
    public static void relaciones2CSV(String rutaReportes)throws IOException{
        String salidaCSV=rutaReportes+"\\MapaRelacionesImap.csv"; 
        relaciones=XML_IMAP_To_Report.listaUniones;
        
        FileWriter fW=new FileWriter(salidaCSV);
        PrintWriter pW = new PrintWriter(fW);
        /*Cabecera*/
        pW.println("COD_JOIN;LEFT_TABLE;RIGHT_TABLE;CARDINALITY;EXPRESSION");
        for(Join j:relaciones){
            pW.println(j.join2String());
        }     
        pW.close(); 
        System.out.println(rutaReportes+"\\MapaRelacionesImap.csv Generado correctamente");
    } 
}
