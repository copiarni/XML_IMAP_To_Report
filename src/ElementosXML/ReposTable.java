/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementosXML;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import Primarios.XML_IMAP_To_Report;

/**
 *
 * @author Innova-TSN
 */
public class ReposTable extends ElementoXML{
   ArrayList<CampoOrigen> listaCamposOrigen;
   private Node nodoDatos;
   private String fullName;
    
    public ReposTable(){
       super(); 
    }
    
    public ReposTable(String name, String obj, Node nodoDatos, String fatherName){
        super(name, obj);
        this.nodoDatos=nodoDatos;
        this.fullName=fatherName+"."+name;
        listaCamposOrigen=new ArrayList<>();
        setListaCamposOrigen();
    }
    
    public void setListaCamposOrigen(){
        if(nodoDatos.getAttributes().getNamedItem("charCols")!=null){
            String enumCharCols= nodoDatos.getAttributes().getNamedItem("charCols").getNodeValue();
            String[] arraycharCols=enumCharCols.split(";");
            for(String s:arraycharCols){                
                CampoOrigen campoOrigen=new CampoOrigen(s.split(",")[1],s.split(",")[0], "char",fullName);
                listaCamposOrigen.add(campoOrigen);
                XML_IMAP_To_Report.listaElementosXML.add(campoOrigen);
            }
        }
        if(nodoDatos.getAttributes().getNamedItem("numCols")!=null){
            String enumNumCols= nodoDatos.getAttributes().getNamedItem("numCols").getNodeValue();
            String[] arrayNumCols=enumNumCols.split(";");
            for(String s:arrayNumCols){                
                CampoOrigen campoOrigen=new CampoOrigen(s.split(",")[1],s.split(",")[0], "num",fullName);
                listaCamposOrigen.add(campoOrigen);
                XML_IMAP_To_Report.listaElementosXML.add(campoOrigen);
            }
        }
    }
    
   
    
}
