/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataItems;

/**
 *
 * @author Innova-TSN
 */
public class DataTabla {
    
    private String nombre;
    private String reposTable;
    private String reposTableId;
    private String obj;
    
    public DataTabla(){        
    }
    
    public DataTabla(String nombre, String reposTableId, String obj){
        this.nombre=nombre;
        this.reposTableId=reposTableId;
        this.obj=obj;
    }
    
    public void setReposTable(String reposTabName){
        this.reposTable=reposTabName;
    }
    
    public String getObj(){
        return obj;
    }
    
    public String getReposTable(){
        return reposTable;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String describir(){
        return nombre+"||"+obj+"||"+reposTable+"||"+reposTableId;
    }
}
