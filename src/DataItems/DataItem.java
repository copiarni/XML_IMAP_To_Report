/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataItems;

import ElementosXML.CampoOrigen;
import ElementosXML.ElementoXML;
import org.w3c.dom.Node;
import Primarios.XML_IMAP_To_Report;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author Innova-TSN
 */
public class DataItem {
    
    Node nodoDatos;
    Expresion expresion;
    String desc;
    String fmt;
    String identity;
    String label;
    String obj;
    String reference;
    
    public DataItem(Node nodoDatos){
        this.nodoDatos=nodoDatos;
        configurarDataItem();
        relacionarDataItem();
    }

    private void configurarDataItem(){
        if(nodoDatos.getAttributes().getNamedItem("desc")!=null){
                this.desc=(nodoDatos.getAttributes().getNamedItem("desc").getNodeValue()).replaceAll("\n","");
                this.desc=desc.replaceAll("\r","");
        }
        if(nodoDatos.getAttributes().getNamedItem("fmt")!=null){
                this.fmt=nodoDatos.getAttributes().getNamedItem("fmt").getNodeValue();
        }
        if(nodoDatos.getAttributes().getNamedItem("identity")!=null){
               this.identity=nodoDatos.getAttributes().getNamedItem("identity").getNodeValue(); 
        }
        if(nodoDatos.getAttributes().getNamedItem("label")!=null){
               this.label=nodoDatos.getAttributes().getNamedItem("label").getNodeValue();
        }
        if(nodoDatos.getAttributes().getNamedItem("obj")!=null){
               this.obj=nodoDatos.getAttributes().getNamedItem("obj").getNodeValue();
        }
        if(nodoDatos.getAttributes().getNamedItem("ref1")!=null){
               this.reference=nodoDatos.getAttributes().getNamedItem("ref1").getNodeValue();
               if(reference.startsWith("Expr:DSColumn:")){
                   reference=reference.replaceAll("Expr:DSColumn:","");
               }
        }else{            
            Node nodoExpresion=((Element)nodoDatos).getElementsByTagName("ResourceAwareStringExpr").item(0);
            expresion=new Expresion(nodoExpresion.getAttributes(),this);      
        }
    }
    
        
    private void relacionarDataItem(){
        if(reference!=null){
            for(ElementoXML c:XML_IMAP_To_Report.listaElementosXML){
                if(c.getClass().equals(CampoOrigen.class)){
                    CampoOrigen cOrigen=((CampoOrigen)c).isAsociated(reference);
                    if(cOrigen!=null){
                        this.reference=cOrigen.getFullName();
                    }
                }
            }
        }
    }
    
    public String buscarCampoRelacionado(String id){
        String nombreCampo="";
        if(id!=null){
            for(ElementoXML c:XML_IMAP_To_Report.listaElementosXML){
                if(c.getClass().equals(CampoOrigen.class)){
                    CampoOrigen cOrigen=((CampoOrigen)c).isAsociated(id);
                    if(cOrigen!=null){
                        nombreCampo=cOrigen.getFullName();
                    }
                }
            }
        }
        return nombreCampo;
    }
    
    
    public String getDesc() {
        return (desc!=null)?desc:"";        
    }

    public String getFmt() {
        return (fmt!=null)?fmt:"";
    }

    public String getIdentity() {
        return (identity!=null)?identity:"";
    }

    public String getLabel() {
        return (label!=null)?label:"";
    }

    public String getObj() {
        return (obj!=null)?obj:"";
    }

    public String getReference() {
        return (reference!=null)?reference:"";
    }
    
    public void setReference(String reference){
        this.reference=reference;
    }
    
    
        
}
