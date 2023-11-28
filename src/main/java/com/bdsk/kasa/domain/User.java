package com.bdsk.kasa.domain;

import java.util.UUID;

public class User {
    private int id = UUID.randomUUID().hashCode();
    private String login;
    private String password;
    private boolean act;
    //private ... Role


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAct() {
        return act;
    }

    public void setAct(boolean act) {
        this.act = act;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 29 * result + getLogin().hashCode();
        result = 29 * result + getPassword().hashCode();
        return result;
    }
}
