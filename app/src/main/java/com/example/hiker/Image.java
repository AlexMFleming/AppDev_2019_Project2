package com.example.hiker;

public class Image {
    private long tr_id;
    private String filename;

    public Image(long id, String filename){
        this.tr_id=id;
        this.filename = filename;
    }
    public long getTr_id(){
        return tr_id;
    }
    public void setTr_id(long tr_id){
        this.tr_id = tr_id;
    }
    public String getFilename(){
        return filename;
    }
}