/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementosXML;

/**
 * Objeto comun en XML. El resto de elementos deben heredar de este
 * @author Innova-TSN
 */
public class ElementoXML {
    String name;
    String obj;
    
    public ElementoXML(){
        
    }
    
    public ElementoXML(String name, String obj){
        this.name=name;
        this.obj=obj;
    }
    
    public String getobj(){
        return obj;
    }
    
    public String getName(){
        return name;
    }

       
    
}
