package com.example.pointtopoint;

public class orders {
    String orderid;
    String ordertype;
    String price;
    String userfullname;
    String userid;
    String username;
    String useremail;
    String usernumber;

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




    public orders(){

    };

    public orders(String orderid, String ordertype, String price, String userfullname, String userid, String username, String useremail, String usernumber) {
        this.orderid = orderid;
        this.ordertype = ordertype;
        this.price = price;
        this.userfullname = userfullname;
        this.userid = userid;
        this.username = username;
        this.useremail = useremail;
        this.usernumber = usernumber;
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
