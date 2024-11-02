package com.OlympusRiviera.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="Landmark")
public class Landmark {

    @Id
    private String landmarkId;
    private String name;
    private String description;
    private String location;
    private String category;
    private Float rating;

    // No-argument constructor
    public Landmark() {
        // Optionally initialize defaults here if necessary
    }
    public Landmark(String landmarkId, String name, String description, String location, String category, Float rating) {
        this.landmarkId = generateLandmarkId();
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.rating = rating;
        this.updatedAt = new Date();
    }

    private String generateLandmarkId() {
        return UUID.randomUUID().toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.landmarkId == null || this.landmarkId.trim().isEmpty()) {
            this.landmarkId = generateLandmarkId();
        }
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    //Created at stamp time
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //Updated at stamp time
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    // Getters and setters

    public String getLandmarkId() {
        return landmarkId;
    }

    public void setLandmarkId(String landmarkId) {
        this.landmarkId = landmarkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}