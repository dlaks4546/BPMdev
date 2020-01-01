package com.example.BPMdev;

/**
 * Created by PC on 2016-05-31.
 */
public class Cart_Item {
    String image;
    String title;
    String price;

    String getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getPrice(){return this.price;}

    Cart_Item(String image, String title, String price){
        this.image=image;
        this.title=title;
        this.price=price;
    }
}
