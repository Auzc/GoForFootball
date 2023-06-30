package com.example.bar.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobObject {

    private String user_name;
    private String password;
    private BmobFile HeadPhoto;
    private String name;
    private String gender;
    private String location;
    private int age;
    private int height;
    private int weight;
    private String signature;
    private int phone;
    private String email;
    private int user_avatar;
    public int getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(int user_avatar) {
        this.user_avatar = user_avatar;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BmobFile getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(BmobFile headPhoto) {
        HeadPhoto = headPhoto;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
