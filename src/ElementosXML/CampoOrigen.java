/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementosXML;

import java.util.ArrayList;

/**
 *
 * @author Innova-TSN
 */
public class CampoOrigen extends ElementoXML{
    private String tipoDato;
    private String fullName;
    private ArrayList<String> dataItemsAsociados;
   
    
    public CampoOrigen(){
        super();
    }
    
    public CampoOrigen(String name, String obj,String tipoDato,String fatherName){
        super(name,obj);
        this.tipoDato=tipoDato;
        this.fullName=fatherName+"."+name;
        dataItemsAsociados=new ArrayList<>();
    }
    
    public String getFullName(){
        return fullName;
    }
    
    public String getDataItemsAsociados(){
        String dIAs="";
        if(dataItemsAsociados.size()>0){
            for (String e:dataItemsAsociados){
                dIAs+=e+";";
            }
        }
        return dIAs;
    }
    
    public CampoOrigen isAsociated(String dataItemId){        
        CampoOrigen co=null;
        for(String s:dataItemsAsociados){
            if(s.equals(dataItemId)){; 
            co=this;
            break;
            }
        }
        return co;
    }
    
    public void setDataItemsAsociados(String dataItemId){
        dataItemsAsociados.add(dataItemId);        
    }
}
