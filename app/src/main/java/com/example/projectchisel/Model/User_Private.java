package com.example.projectchisel.Model;

//        "email" : "aharshit123456@gmail.com",
//        "uid" : "vvip6UtqFgMi5KI4C9Egcb9ikni1",
//        "username" : "aharshit123456"

public class User_Private {
    public String email;
    public String uid;
    public String username;

    public User_Private(String email, String uid, String username) {
        this.email = email;
        this.uid = uid;
        this.username = username;
    }

    public User_Private(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User_Private{" +
                "email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
