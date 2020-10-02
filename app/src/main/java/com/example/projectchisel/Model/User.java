package com.example.projectchisel.Model;

public class User {
    public UserInfo userInfo;
    public String uid;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(UserInfo userInfo, String uid) {
        this.userInfo = userInfo;
        this.uid = uid;
    }

    public User() {
    }
}
