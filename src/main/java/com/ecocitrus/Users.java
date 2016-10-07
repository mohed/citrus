package com.ecocitrus;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    protected Users() {
    }

    public Users(String name) {
        this.username = name;
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
}
