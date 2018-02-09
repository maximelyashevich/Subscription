package com.elyashevich.subscription.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable, Cloneable{
    private long id;

    Entity(){
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
