package com.bdsk.kasa.domain;

import com.bdsk.kasa.domain.interfaces.Identifiable;

import java.util.UUID;

public class ConfirmedOrder extends Order implements Identifiable {
    private int id = UUID.randomUUID().hashCode();
    private int authorId;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
