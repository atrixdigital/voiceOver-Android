package com.maximus.voicemessenger;

/**
 * Created by Muhammad on 28/08/2016.
 */
public class MsgKey {
    private String key;
    private String Sender;

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getMessage() {
        return key;
    }

    public void setMessage(String message) {
        this.key = key;
    }


    public MsgKey() {

    }

    public MsgKey(String s, String m) {
        this.Sender = s;
        this.key = key;
    }
}