package com.elyashevich.subscription.entity;

import java.util.Objects;

public class Genre extends Entity {
    private String name;

    public Genre(){
    }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        if (!super.equals(o)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='"+getId()+
                " ,name='" + name + '\'' +
                '}';
    }
}
