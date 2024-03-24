package com.example.qlkh.entity;

public class User {
    private String username;
    private String password;
    private String staffname;
    private String createdate;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public User(String username, String password, String staffname, String createdate) {
        this.username = username;
        this.password = password;
        this.staffname = staffname;
        this.createdate = createdate;
    }

    public User() {
    }
}
