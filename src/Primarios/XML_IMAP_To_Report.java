/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primarios;


import DataItems.DataItem;
import java.util.ArrayList;
import ElementosXML.ElementoXML;
import ElementosXML.Folder;


/**
 * La clase principal, llama al resto de metodos e imprime
 * informacion por pantalla.
 * @author Innova-TSN
 */
public class XML_IMAP_To_Report {

    public static LectorXML lector;
    public static ArrayList<ElementoXML> listaElementosXML;
    public static ArrayList<DataItem> listaDataItems;
    public static ArrayList<Folder> listaDirectorios;
    
    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args) throws Exception {
        
        //String archivo="C:\\DIEGO\\Innova_Proyectos\\[TSB]\\IMAP_MDL.xml"; 
        //String rutaSalida="C:\\DIEGO\\Innova_Proyectos\\[TSB]";
        listaElementosXML=new ArrayList<>();
        listaDataItems=new ArrayList<>();
        listaDirectorios=new ArrayList<>();        
        
        String archivo="",rutaSalida="";        
        try {
          archivo=args[0];
          rutaSalida=args[1];
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: Se necesitan parametros de entrada");
            System.out.println("String ruta+ficheroXml, String rutadestinoReportes");
            System.exit(0);
        }
        
        lector=LectorXML.getInstance();
        lector.parseador(archivo);
        

        //printDetalles();
        EscritorCSV.volcadoCSV(rutaSalida);
        EscritorCSV.volcadoTXT(rutaSalida);
    }
    
    /*Muestra mensajes en pantalla. Solo para desarrollo*/
    public static void printDetalles(){
                        
        for(DataItem d:listaDataItems){
            System.out.print(d.getLabel()+";");
            System.out.println(d.getReference());            
            System.out.println(d.getObj());
        }
        
    }
    
}
