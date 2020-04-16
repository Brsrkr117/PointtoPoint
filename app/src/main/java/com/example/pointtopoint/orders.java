package com.example.pointtopoint;

public class orders {
    String orderid;
    String ordertype;
    String price;


    public orders(){

    };

    public orders(String orderid, String ordertype, String price) {
        this.orderid = orderid;
        this.ordertype = ordertype;
        this.price = price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
