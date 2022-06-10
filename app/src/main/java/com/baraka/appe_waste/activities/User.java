package com.baraka.appe_waste.activities;

public class User {
    private String name;
    private String email;
    private String phone;
    private String accType;
    public User(){
    }
    public User(String name, String email, String phone, String accType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.accType = accType;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public void setname(String fullName) {
        this.name = fullName;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phoneNum) {
        this.phone = phoneNum;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }
}

