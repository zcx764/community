package com.cx.community.model;

public class User {
    private int id;
    private String name;
    private String accountId;
    private String token;
    private long getCreate;
    private long getModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getGetCreate() {
        return getCreate;
    }

    public void setGetCreate(long getCreate) {
        this.getCreate = getCreate;
    }

    public long getGetModified() {
        return getModified;
    }

    public void setGetModified(long getModified) {
        this.getModified = getModified;
    }
}
