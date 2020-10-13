/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataItems;

import ElementosXML.CampoOrigen;
import ElementosXML.ElementoXML;
import Primarios.XML_IMAP_To_Report;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Innova-TSN
 */
public class JoinExpresion {
    private Node nodo;
    private String exDepurada;
    
    public JoinExpresion(){
        
    }
    
    public JoinExpresion(Node nodo){
        this.nodo=nodo;
        exDepurada="";
        recogerExpresion();
    }
    
    private void recogerExpresion(){
        /*Obtener nodo hijo de JoinCondition*/
        Node n=nodo.getChildNodes().item(1);
        if(n.getNodeName().equals("Compare")){           
            exDepurada=expresionSimple(n);
        }else if (n.getNodeName().equals("ConditionalExprAdapter")){
            //Nodo ResourceAwareStringExpr
            Node nd=n.getChildNodes().item(1).getChildNodes().item(1);
            exDepurada=expresionCompuesta(nd);
        }else if (n.getNodeName().equals("CompoundConditionalExpr")){
            //Nodos LeftExpr y RightExpr
            String op=n.getAttributes().getNamedItem("operator").getNodeValue();
            Node izq=n.getChildNodes().item(1).getChildNodes().item(1);
            Node der=n.getChildNodes().item(3).getChildNodes().item(1);            
            exDepurada="("+expresionSimple(izq)+")"+op+"("+expresionSimple(der)+")";     
        }      
    }
    
    
    private String searchDataItem(String reference){
        for(ElementoXML el:XML_IMAP_To_Report.listaElementosXML){
            if(el instanceof CampoOrigen){
                CampoOrigen co=((CampoOrigen) el).isAsociated(reference);
                if(co != null){
                    return co.getName();
                }                
            }
        }
        return "";
    }
    
    /*Forma una frase simple de tipo campo+operador+campo*/
    private String expresionSimple(Node n){
        String operator,left,right;
            operator=n.getAttributes().getNamedItem("operator").getNodeValue();
            left=n.getAttributes().getNamedItem("ref1").getNodeValue().split(":")[2];
            right=n.getAttributes().getNamedItem("ref2").getNodeValue().split(":")[2];
            left=searchDataItem(left);
            right=searchDataItem(right);
            return left+" "+operator+" "+right;
    }
    
    private String expresionCompuesta(Node n){
        if(n.getAttributes().getNamedItem("exprType")!=null){
            n.getAttributes().removeNamedItem("exprType");
        }
        if(n.getAttributes().getNamedItem("scope")!=null){
            n.getAttributes().removeNamedItem("scope");
        }
        String[] listaCampos = n.getAttributes().getNamedItem("ref1").getNodeValue().split(":")[2].split(",");
        ArrayList<String> listaNombres=new ArrayList<String>();
        for(String a:listaCampos){
            listaNombres.add(searchDataItem(a));
        }
        
        n.getAttributes().removeNamedItem("ref1");
        String expresion="";
        int totIt=n.getAttributes().getLength()+listaNombres.size();
        for(int i=1;i<=totIt;i++){
            if(n.getAttributes().getNamedItem("cmp"+i)!=null){
                expresion+=n.getAttributes().getNamedItem("cmp"+i).getNodeValue().replace("Text:", "");
            }else{
                expresion+=listaNombres.remove(0);                
            }
        }
        return expresion;
    }
    
    /*Devuelve la expresion formada*/
    public String getExDepurada(){
        return exDepurada;
    }
}
