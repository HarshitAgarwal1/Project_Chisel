package com.example.projectchisel.Model;

import android.widget.CheckBox;

public class Chat {
    public String sender;
    public String reciever;
    public String msg;

    @Override
    public String toString() {
        return "Chat{" +
                "sender='" + sender + '\'' +
                ", reciever='" + reciever + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * @param sender Sender User Id (MY ID or User ID)
     * @param reciever Reciever User If (MY ID or User ID)
     * @param msg : Recieving Text
     */
    public Chat(String sender, String reciever, String msg) {
        this.sender = sender;
        this.reciever = reciever;
        this.msg = msg;
    }
    public Chat(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
