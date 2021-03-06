package com.elyashevich.subscription.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Subscription extends Entity{
    private User user;
    private LocalDate subscriptionRegistration;
    private LocalDate subscriptionFinish;
    private BigDecimal price;
    private PaperEdition paperEdition;

    public Subscription() {
    }

    public Subscription(LocalDate subscriptionRegistration, LocalDate subscriptionFinish, BigDecimal price) {
        this.subscriptionRegistration = subscriptionRegistration;
        this.subscriptionFinish = subscriptionFinish;
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getSubscriptionRegistration() {
        return subscriptionRegistration;
    }

    public void setSubscriptionRegistration(LocalDate subscriptionRegistration) {
        this.subscriptionRegistration = subscriptionRegistration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaperEdition getPaperEdition() {
        return paperEdition;
    }

    public void setPaperEdition(PaperEdition paperEdition) {
        this.paperEdition = paperEdition;
    }

    public LocalDate getSubscriptionFinish() {
        return subscriptionFinish;
    }

    public void setSubscriptionFinish(LocalDate subscriptionFinish) {
        this.subscriptionFinish = subscriptionFinish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        if (!super.equals(o)) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(subscriptionRegistration, that.subscriptionRegistration) &&
                Objects.equals(subscriptionFinish, that.subscriptionFinish) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), subscriptionRegistration, subscriptionFinish, price);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + getId() +
                ", user=" + user +
                ", paperEdition"+paperEdition+
                ", subscriptionRegistration=" + subscriptionRegistration +
                ", subscriptionFinish=" + subscriptionFinish +
                ", price=" + price +
                "}\n";
    }
}
