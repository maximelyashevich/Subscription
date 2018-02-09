package com.elyashevich.subscription.entity;

import java.util.Objects;

public class Address extends Entity{
    private String country;
    private String postIndex;
    private String city;
    private String detailAddress;

    public Address() {
    }

    public Address(String country, String postIndex, String city, String detailAddress) {
        this.country = country;
        this.postIndex = postIndex;
        this.city = city;
        this.detailAddress = detailAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(String postIndex) {
        this.postIndex = postIndex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getId() == address.getId() &&
                Objects.equals(country, address.country) &&
                Objects.equals(postIndex, address.postIndex) &&
                Objects.equals(city, address.city) &&
                Objects.equals(detailAddress, address.detailAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), country, postIndex, city, detailAddress);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + getId() +
                ", country='" + country + '\'' +
                ", postIndex='" + postIndex + '\'' +
                ", city='" + city + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}

