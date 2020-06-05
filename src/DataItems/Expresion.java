/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataItems;

import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author Innova-TSN
 */
public class Expresion {
    
    NamedNodeMap atributosExpresion;
    String resources;
    String formula;
    DataItem diPadre;
    
    public Expresion(){
        
    }
    
    public Expresion(NamedNodeMap atributosExpresion,DataItem ditem){
        this.atributosExpresion=atributosExpresion;
        this.diPadre=ditem;
        formatearExpresion();
    }
    
    public void formatearExpresion(){
        /*Eliminar el atributo scope*/
        if(atributosExpresion.getNamedItem("scope")!=null){
            atributosExpresion.removeNamedItem("scope");
        }
        /*Eliminar el atributo exprType*/
        if(atributosExpresion.getNamedItem("exprType")!=null){
            atributosExpresion.removeNamedItem("exprType");
        }
        /*Guardar el atributo ref1. Solo cuando exista un unico atributo*/
        if((atributosExpresion.getLength()==1)&&(atributosExpresion.getNamedItem("ref1")!=null)){
            resources=atributosExpresion.getNamedItem("ref1").getNodeValue();
            resources=resources.replaceAll("Resources:DSColumn:", "");
            atributosExpresion.removeNamedItem("ref1");
            if(diPadre.getReference()==""){
                diPadre.setReference(resources);
            }
        }
        if((atributosExpresion.getNamedItem("cmp1")!=null)&&(atributosExpresion.getNamedItem("ref1")!=null)){
            resources=atributosExpresion.getNamedItem("ref1").getNodeValue();
            resources=resources.replaceAll("Resources:DSColumn:", "");
            atributosExpresion.removeNamedItem("ref1");
            String newRef="";
            int i=1;
            while(atributosExpresion.getLength()>0){
                if(atributosExpresion.getNamedItem("cmp"+i)!=null){
                    newRef+=atributosExpresion.getNamedItem("cmp"+i).getNodeValue();
                    atributosExpresion.removeNamedItem("cmp"+i);
                }
                i++;
            }
            //for(int i=0;i<atributosExpresion.getLength();i++){
            //    newRef+=atributosExpresion.item(i).getNodeValue();
            //}
            newRef=newRef.replaceAll("\n","");
            newRef=newRef.replaceAll("\r","");
            newRef=newRef.replaceAll("\t","");
            
            if(resources.split(",").length==1){                
                resources=diPadre.buscarCampoRelacionado(resources);                
                newRef=newRef.replaceFirst("Text:", "");
                newRef=newRef.replaceAll("Text:", resources);
                if(diPadre.getReference()==""){
                diPadre.setReference(newRef);
                }
            }else{
                newRef=newRef.replaceFirst("Text:", "");
                String[] arrayRecursos=resources.split(",");
                for(String s: arrayRecursos){                    
                    s=diPadre.buscarCampoRelacionado(s);
                    newRef=newRef.replaceFirst("Text:", s);
                }           
                if(diPadre.getReference()==""){
                diPadre.setReference(newRef);
                }                
            }
            
        }
        
    
        
        
        
        
        String test="";
        for(int i=0;i<atributosExpresion.getLength();i++){
            test+=atributosExpresion.item(i).getNodeValue();
        }
        
        //System.out.println(test);
        
        //System.out.println(resources);
    }
    
    
    
    
}
