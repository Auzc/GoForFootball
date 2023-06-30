package com.example.bar.database;

import cn.bmob.v3.BmobObject;

public class StoreMessage extends BmobObject {
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    private String user_id;
    private String room_name;

    public StoreMessage(String user_id, String room_id) {
        this.user_id = user_id;
        room_name = room_id;
    }
    public StoreMessage() {

    }



}
