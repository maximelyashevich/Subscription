package com.elyashevich.subscription.entity;

import com.elyashevich.subscription.command.ClientType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String userName;
    private String email;
    private String password;
    private ClientType type=ClientType.USER;
    private BigDecimal amount=new BigDecimal(10);
    private Address address;
    private boolean availability=true;
    private String imagePath;

    public User() {
    }

    public User(String userName, String password,  ClientType type, String email, boolean availability, String firstName, String lastName, LocalDate birthday,  BigDecimal amount ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.amount = amount;
        this.availability = availability;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return availability == user.availability &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                type == user.type &&
                Objects.equals(amount, user.amount) &&
                Objects.equals(address, user.address) &&
                Objects.equals(imagePath, user.imagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, birthday, userName, email, password, type, amount, address, availability, imagePath);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId()+
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", addressId=" + address +
                ", availability=" + availability +
                '}';
    }
}
