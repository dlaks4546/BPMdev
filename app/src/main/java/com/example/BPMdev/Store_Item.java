package com.example.BPMdev;

public class Store_Item {
    String image;
    String title;
    String price;
    String itemno;

    String getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getPrice(){return this.price;}
    String getItemNo(){return this.itemno;}

    Store_Item(String itemno, String image, String title, String price){
        this.image=image;
        this.title=title;
        this.price=price;
        this.itemno=itemno;
    }
}
