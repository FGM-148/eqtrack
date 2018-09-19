package com.ms.et.domain;

import javax.persistence.*;

@Entity
@Table(name = "Users")

public class User {
    @Id
    @Column(name = "username")
    private String mName;
    @Column(name = "password")
    private String mPassword;
    @Column(name = "enabled")
    private boolean mEnabled;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}