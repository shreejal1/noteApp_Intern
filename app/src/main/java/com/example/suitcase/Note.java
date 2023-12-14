package com.example.suitcase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String price;
    @ColumnInfo
    public String content;
    @ColumnInfo
    public String purchased;

    public Note(int uid, String title, String price, String content, String purchased) {
        this.uid = uid;
        this.title = title;
        this.price = price;
        this.content = content;
        this.purchased = purchased;
    }
    @Ignore
    public Note(String title, String price, String content, String purchased){
        this.title = title;
        this.price = price;
        this.content = content;
        this.purchased = purchased;
    }

    public Note() {

    }

    public int getUid(){
        return uid;
    }
    public void setUid(int uid){
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPurchased() {
        return purchased;
    }

    public void setPurchased(String purchased) {
        this.purchased = purchased;
    }


}