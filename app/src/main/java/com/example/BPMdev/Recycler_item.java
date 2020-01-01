package com.example.BPMdev;
/**
 * Created by PC on 2016-05-03.
 */
public class Recycler_item {
    String content;
    String title;

    String getContent(){
        return this.content;
    }
    String getTitle(){
        return this.title;
    }

    Recycler_item(String content, String title){
        this.content=content;
        this.title=title;
    }
}
