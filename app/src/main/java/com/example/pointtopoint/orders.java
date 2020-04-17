package com.example.pointtopoint;

public class orders {

    String orderstatus;
    String orderotp;
    String orderid;
    String ordertype;
    String price;
    String userfullname;
    String userid;
    String username;
    String useremail;
    String usernumber;
    String riderfullname;
    String ridername;
    String riderid;
    String ridernumber;
    String rideremail;
    String pickuplat,pickuplong;
    String droplat,droplong;
    String pickaddr;
    String dropaddr;

    public orders(){

    };

    public orders(String orderstatus, String orderotp, String orderid, String ordertype, String price, String userfullname, String userid, String username, String useremail, String usernumber, String riderfullname, String ridername, String riderid, String ridernumber, String rideremail, String pickuplat, String pickuplong, String droplat, String droplong, String pickaddr, String dropaddr) {
        this.orderstatus = orderstatus;
        this.orderotp = orderotp;
        this.orderid = orderid;
        this.ordertype = ordertype;
        this.price = price;
        this.userfullname = userfullname;
        this.userid = userid;
        this.username = username;
        this.useremail = useremail;
        this.usernumber = usernumber;
        this.riderfullname = riderfullname;
        this.ridername = ridername;
        this.riderid = riderid;
        this.ridernumber = ridernumber;
        this.rideremail = rideremail;
        this.pickuplat = pickuplat;
        this.pickuplong = pickuplong;
        this.droplat = droplat;
        this.droplong = droplong;
        this.pickaddr = pickaddr;
        this.dropaddr = dropaddr;
    }

    public String getOrderotp() {
        return orderotp;
    }

    public void setOrderotp(String orderotp) {
        this.orderotp = orderotp;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
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

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getRiderfullname() {
        return riderfullname;
    }

    public void setRiderfullname(String riderfullname) {
        this.riderfullname = riderfullname;
    }

    public String getRidername() {
        return ridername;
    }

    public void setRidername(String ridername) {
        this.ridername = ridername;
    }

    public String getRiderid() {
        return riderid;
    }

    public void setRiderid(String riderid) {
        this.riderid = riderid;
    }

    public String getRidernumber() {
        return ridernumber;
    }

    public void setRidernumber(String ridernumber) {
        this.ridernumber = ridernumber;
    }

    public String getRideremail() {
        return rideremail;
    }

    public void setRideremail(String rideremail) {
        this.rideremail = rideremail;
    }

    public String getPickuplat() {
        return pickuplat;
    }

    public void setPickuplat(String pickuplat) {
        this.pickuplat = pickuplat;
    }

    public String getPickuplong() {
        return pickuplong;
    }

    public void setPickuplong(String pickuplong) {
        this.pickuplong = pickuplong;
    }

    public String getDroplat() {
        return droplat;
    }

    public void setDroplat(String droplat) {
        this.droplat = droplat;
    }

    public String getDroplong() {
        return droplong;
    }

    public void setDroplong(String droplong) {
        this.droplong = droplong;
    }

    public String getPickaddr() {
        return pickaddr;
    }

    public void setPickaddr(String pickaddr) {
        this.pickaddr = pickaddr;
    }

    public String getDropaddr() {
        return dropaddr;
    }

    public void setDropaddr(String dropaddr) {
        this.dropaddr = dropaddr;
    }
}
