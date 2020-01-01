package com.example.BPMdev;

/**
 * Created by PC on 2016-06-10.
 */
public class Order_Item {
    String productname;
    String price;
    String date;
    String deliveryno;

    String getProductName(){
        return this.productname;
    }
    String getPrice(){return this.price;}
    String getDate(){return this.date;}
    String getDeliveryno(){return  this.deliveryno;}

    Order_Item(String productname, String price, String date, String deliveryno){
        this.productname=productname;
        this.price=price;
        this.date=date;
        this.deliveryno=deliveryno;
    }
}
