package com.example.fyprojectnew.Models;

public class User {
    public String username;
    public String cnic;
    public String gmail;
    public String contact;
    public String city;
    public String password;
    public String profile_pic;

    public User() {
    }

    public User(String username, String cnic, String gmail, String contact, String city, String password,String profile_pic) {
        this.username = username;
        this.cnic = cnic;
        this.gmail = gmail;
        this.contact = contact;
        this.city = city;
        this.password = password;
        this.profile_pic = profile_pic;
    }
}
