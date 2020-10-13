/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primarios;


import DataItems.DataItem;
import DataItems.DataTabla;
import java.util.ArrayList;
import ElementosXML.ElementoXML;
import ElementosXML.Folder;
import ElementosXML.Join;


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
    public static ArrayList<Join> listaUniones;
    public static ArrayList<DataTabla> listaDatTablas;
    
    /**
     * @param args the command line arguments
     */    
    /*Para ejecutar desde IDE:
        -Descomentar String archivo=C\\DIEGO..., String rutaSalida y printDetalles()
        -Comentar el bloque del String archivo="" y todo el try catch
      Antes de construir el paquete de ejecucion:
        -Comentar String archivo=C\\DIEGO..., String rutaSalida y printDetalles()
        -Descomentar el bloque del String archivo="" y todo el try catch*/
    public static void main(String[] args) throws Exception {
        
        String archivo="C:\\DIEGO\\Innova_Proyectos\\[Naturgy]\\IMAP_NATURGY.xml";
        String rutaSalida="C:\\DIEGO\\Innova_Proyectos\\[Naturgy]";
        listaElementosXML=new ArrayList<>();
        listaDataItems=new ArrayList<>();
        listaDirectorios=new ArrayList<>(); 
        listaDatTablas=new ArrayList<>();
        listaUniones=new ArrayList<>();
        
        /*String archivo="",rutaSalida="";        
        try {
          archivo=args[0];
          rutaSalida=args[1];
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: Se necesitan parametros de entrada");
            System.out.println("String ruta+ficheroXml, String rutadestinoReportes");
            System.exit(0);
        }*/
        
        lector=LectorXML.getInstance();
        lector.parseador(archivo);
        

/*        printDetalles();*/
        EscritorCSV.volcadoCSV(rutaSalida);
        EscritorCSV.volcadoTXT(rutaSalida);
        EscritorCSV.relaciones2CSV(rutaSalida);
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
