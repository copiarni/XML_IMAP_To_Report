/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementosXML;

import DataItems.DataTabla;
import DataItems.JoinExpresion;
import Primarios.XML_IMAP_To_Report;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Innova-TSN
 */
public class Join {
    
    private Node nodoDatos;
    private JoinExpresion joinExpresion;
    private String identidad;
    private String cardinalidad="";
    private String lTable,rTable;
    private String lTableName,rTableName;
    
    public Join(){
        
    }
    
    public Join(Node nodoDatos){
        this.nodoDatos=nodoDatos;
        configurarJoin();
        setTableName(lTable, "L");
        setTableName(rTable, "R");
        this.joinExpresion=new JoinExpresion(nodoDatos.getChildNodes().item(1));
        System.out.println(join2String());
    }
    
    private void configurarJoin(){
        if(nodoDatos.getAttributes().getNamedItem("identity")!=null){
                this.identidad=(nodoDatos.getAttributes().getNamedItem("identity").getNodeValue());                               
        }
        if(nodoDatos.getAttributes().getNamedItem("card")!=null){
                this.cardinalidad=(nodoDatos.getAttributes().getNamedItem("card").getNodeValue());                                
        }
        if(nodoDatos.getAttributes().getNamedItem("ref1")!=null){
                this.lTable=(nodoDatos.getAttributes().getNamedItem("ref1").getNodeValue().split(":"))[2];                               
        }
        if(nodoDatos.getAttributes().getNamedItem("ref2")!=null){
                this.rTable=(nodoDatos.getAttributes().getNamedItem("ref2").getNodeValue().split(":"))[2];                              
        }              
    }
    
    /**args: -table->lTable o rTable
    *        -tableName-> lTableName o rTableName
    */
    private void setTableName(String table,String tableName){
        for(DataTabla daTab:XML_IMAP_To_Report.listaDatTablas){            
            if(table.equals(daTab.getObj())){
                if(tableName.equals("L")){
                    lTableName=daTab.getReposTable();
                }else if (tableName.equals("R")){
                    rTableName=daTab.getReposTable();
                }                
                break;
            }
        }
    }
    
   
    
    public String join2String(){
        return identidad+";"+lTableName+";"+rTableName+";"+cardinalidad+";"+joinExpresion.getExDepurada();
    }
    
}
