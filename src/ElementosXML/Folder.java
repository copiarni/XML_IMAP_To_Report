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
public class Folder {
 
    private String folderName;
    private String folderId;
    private String idFather;
    private int valorJerarquia;
    private ArrayList<String> subfolders;
    private ArrayList<String> dataItems;
    
    
    public Folder(){
        
    }
    public Folder(String folderName, String folderId){
        this.folderName=folderName;
        this.folderId=folderId;
        this.subfolders=new ArrayList<>();
        this.dataItems=new ArrayList<>();
    }
    public Folder(String folderName, String folderId, String idFather){
        this.folderName=folderName;
        this.folderId=folderId;
        this.idFather=idFather;
        this.subfolders=new ArrayList<>();
        this.dataItems=new ArrayList<>();
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getIdFather() {
        return idFather;
    }

    public ArrayList<String> getSubfolders() {
        return subfolders;
    }

    public ArrayList<String> getDataItems() {
        return dataItems;
    }
    
    public String getResumen(){
        return folderName;
    }
    
    public int getQtyDataItems(){
        return dataItems.size();
    }
    
    public int getValorJerarquia(){
        return valorJerarquia;
    }
    
    public void setValorJerarquia(int valorJerarquia){
        this.valorJerarquia=valorJerarquia;
    }
    
    public void SetDataItems(String listaItems){
        
        listaItems=listaItems.replaceAll("FolderItems:DataItem:", "");
        String[] items=listaItems.split(",");
        for(int i=0;i<items.length;i++){
            addDataItem(items[i]);
        }
        
    }
    
    public void addSubfolder(String idSon){
        subfolders.add(idSon);
    }
    
    public void addDataItem(String itemId){
        dataItems.add(itemId);
    }
    
    
}

