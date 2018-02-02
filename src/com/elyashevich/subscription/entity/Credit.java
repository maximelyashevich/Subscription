package com.elyashevich.subscription.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Credit extends Entity {
    private long id;
    private BigDecimal debt;
    private BigDecimal interestRate;
    private int payoff;
    private boolean availability;
    private List<User>users = new ArrayList<>();

    public Credit() {
    }

    public Credit(BigDecimal debt, BigDecimal interestRate, int payoff, boolean availability) {
        this.debt = debt;
        this.interestRate = interestRate;
        this.payoff = payoff;
        this.availability = availability;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public void addUser(User user) {
        this.users.add(user);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public int getPayoff() {
        return payoff;
    }

    public void setPayoff(int payoff) {
        this.payoff = payoff;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean available) {
        availability = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return id == credit.id && payoff ==credit.payoff &&
                availability == credit.availability &&
                Objects.equals(debt, credit.debt) &&
                Objects.equals(interestRate, credit.interestRate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, debt, interestRate, payoff, availability);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", debt=" + debt +
                ", interestRate=" + interestRate +
                ", payoff=" + payoff +
                ", availability=" + availability +
                '}';
    }
}
