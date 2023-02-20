package com.example.fyprojectnew;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
public class Post {
    public String uid;
    public String username;
    public String cnic;
    public String gmail;
    public String contact;
    public String city;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();


    public Post(String uid, String username, String cnic, String gmail,String contact,String city) {
        this.uid=uid;
        this.username = username;
        this.cnic = cnic;
        this.gmail = gmail;
        this.contact = contact;
        this.city = city;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid",uid);
        result.put("name", username);
        result.put("city", city);
        result.put("cnic", cnic);
        result.put("gmail", gmail);
        result.put("contact", contact);
        return result;
    }
}
