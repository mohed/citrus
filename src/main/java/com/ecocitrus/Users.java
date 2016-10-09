package com.ecocitrus;

import javax.persistence.*;

/**
 * Created by Administrator on 2016-10-05.
 */
@Entity
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String username;
    private int password;

    protected Users() {
    }

    public Users(String name, int password) {
        this.username = name;
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
