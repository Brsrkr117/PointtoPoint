package com.example.pointtopoint;

public class orders {
    String OrderType;
    String OrderPrice;
    String OrderID;
    String Pickupadd;
    String Dropadd;
    Float Plat,Plong;
    Float Dlat,Dlong;

    public orders(){

    }

    public orders(String orderType, String orderPrice, String orderID, String pickupadd, String dropadd, Float plat, Float plong, Float dlat, Float dlong) {
        OrderType = orderType;
        OrderPrice = orderPrice;
        OrderID = orderID;
        Pickupadd = pickupadd;
        Dropadd = dropadd;
        Plat = plat;
        Plong = plong;
        Dlat = dlat;
        Dlong = dlong;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getPickupadd() {
        return Pickupadd;
    }

    public void setPickupadd(String pickupadd) {
        Pickupadd = pickupadd;
    }

    public String getDropadd() {
        return Dropadd;
    }

    public void setDropadd(String dropadd) {
        Dropadd = dropadd;
    }

    public Float getPlat() {
        return Plat;
    }

    public void setPlat(Float plat) {
        Plat = plat;
    }

    public Float getPlong() {
        return Plong;
    }

    public void setPlong(Float plong) {
        Plong = plong;
    }

    public Float getDlat() {
        return Dlat;
    }

    public void setDlat(Float dlat) {
        Dlat = dlat;
    }

    public Float getDlong() {
        return Dlong;
    }

    public void setDlong(Float dlong) {
        Dlong = dlong;
    }
}
