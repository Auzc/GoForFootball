package com.example.bar.database;

import cn.bmob.v3.BmobObject;

public class ChatMessage extends BmobObject {
    private String user_id;

    public String getChatroom_name() {
        return chatroom_name;
    }

    public void setChatroom_name(String chatroom_name) {
        this.chatroom_name = chatroom_name;
    }

    private String chatroom_name;

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    private String message_content;
    private String time;
    public ChatMessage( String chatroom_name, String userId,String messageContent, String time1) {
        this.user_id = userId;
        this.chatroom_name = chatroom_name;
        this.message_content = messageContent;
        this.time = time1;
    }
    public ChatMessage() {

    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
