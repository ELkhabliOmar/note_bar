package com.example.notebar;

import java.util.Date;
import java.util.Map;

public class Note {
     String id;
     String title;
     String note;
     //String timestamp;
     public Note(){    // Constructeur vide
     }

    public Note(String id ,String title, String note) {
        this.id=id;
        this.title = title;
        this.note = note;
       // this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String title) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
  /*  public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp =timestamp;
    }*/




}




