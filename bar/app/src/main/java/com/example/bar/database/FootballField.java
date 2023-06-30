package com.example.bar.database;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class FootballField extends BmobObject {
    private String name;
    private BmobFile filePhoto;



    private Float address_lat;

    private Float address_lng;

    private String address_name;

    private String field_size;

    private String character;

    public Float getAddress_lat() {
        return address_lat;
    }

    public void setAddress_lat(Float address_lat) {
        this.address_lat = address_lat;
    }

    public Float getAddress_lng() {
        return address_lng;
    }

    public void setAddress_lng(Float address_lng) {
        this.address_lng = address_lng;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getField_size() {
        return field_size;
    }

    public void setField_size(String field_size) {
        this.field_size = field_size;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getFile() {
        return filePhoto;
    }

    public void setFile(BmobFile file) {
        this.filePhoto = file;
    }
}
