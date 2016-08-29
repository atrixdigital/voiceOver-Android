package com.maximus.voicemessenger;

/**
 * Created by Maximus on 8/20/2016.
 */
public class User {

    public String name;
    public String username;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email,String password) {
        this.name = name;
        this.username = email;
        this.password=password;
    }

}
