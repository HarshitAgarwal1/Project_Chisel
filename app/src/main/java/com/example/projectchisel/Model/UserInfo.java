package com.example.projectchisel.Model;

//    "age" : 0,
//    "desc" : "this is the description",
//    "height" : 162,
//    "name" : "Harshit Agarwal",
//    "profile_pic" : "none",
//    "username" : "aharshit123456"

public class UserInfo {
     public String description;
     public String name;
     public String profile_pic;
     public String username;
     public int age;
     public int height;

    /**
     *User Info Model from the firebase Database
     * @param description the description of the persion
     * @param age age of the user
     * @param height Height
     * @param profile_pic dp resource link in string
     * @param name Full name of the person
     * @param username username unique of the person
     */
    public UserInfo(String description, String name, String profile_pic, String username, int age, int height) {
        this.description = description;
        this.name = name;
        this.profile_pic = profile_pic;
        this.username = username;
        this.age = age;
        this.height = height;
    }

    public UserInfo(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}

