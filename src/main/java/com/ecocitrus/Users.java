package com.ecocitrus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016-10-05.
 */
@Entity
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    @NotNull
    private String username;
    @NotNull
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

    public void setPassword(String password) {

        this.password = generateHash(password);
    }

    public int generateHash(String string) {
        int hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }
}
