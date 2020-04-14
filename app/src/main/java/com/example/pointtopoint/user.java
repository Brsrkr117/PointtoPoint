package com.example.pointtopoint;

public class user {
    public String fullName;
    public String Username;
    public String PhoneNumber;
    public String Email;
    public String Role;
    public String CustomerType;


    public user(String fullName, String username, String phoneNumber, String email, String role, String customerType) {
        this.fullName = fullName;
        Username = username;
        PhoneNumber = phoneNumber;
        Email = email;
        Role = role;
        CustomerType = customerType;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }



}

