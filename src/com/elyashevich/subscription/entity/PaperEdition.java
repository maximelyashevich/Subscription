package com.elyashevich.subscription.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class PaperEdition extends Entity{
    private PaperType type;
    private String title;
    private BigDecimal price;
    private String description;
    private int publishingPeriodicity;
    private int ageRestriction;
    private boolean availability=true;
    private String imagePath="\\resource\\image\\periodical.jpg";
    private int durationMonth = 3;
    public PaperEdition() {
    }

    public PaperEdition(PaperType type, String title, BigDecimal price, String description, int publishingPeriodicity, int ageRestriction, boolean availability, String imagePath) {
        this.type = type;
        this.title = title;
        this.price = price;
        this.description = description;
        this.publishingPeriodicity = publishingPeriodicity;
        this.ageRestriction = ageRestriction;
        this.availability = availability;
        this.imagePath = imagePath;
    }

    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublishingPeriodicity() {
        return publishingPeriodicity;
    }

    public void setPublishingPeriodicity(int publishingPeriodicity) {
        this.publishingPeriodicity = publishingPeriodicity;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getDurationMonth() {
        return durationMonth;
    }

    public void setDurationMonth(int durationMonth) {
        this.durationMonth = durationMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEdition that = (PaperEdition) o;
        return publishingPeriodicity == that.publishingPeriodicity &&
                ageRestriction == that.ageRestriction &&
                availability == that.availability &&
                type == that.type &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, title, description, publishingPeriodicity, ageRestriction, availability, imagePath);
    }

    @Override
    public String toString() {
        return "\n\tPaperEdition{" +
                "id= "+getId() +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\n' +
                ", publishingPeriodicity=" + publishingPeriodicity +
                ", ageRestriction=" + ageRestriction +
                ", availability=" + availability +
                ", imagePath='" + imagePath + '\'' +
                "}\n";
    }
}
