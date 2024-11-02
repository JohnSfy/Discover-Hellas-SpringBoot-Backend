package com.OlympusRiviera.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Landmark")
public class Landmark {

    @Id
    private String landmarkId;
    private String name;
    private String description;
    private String location;
    private String category;
    private Float rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // No-argument constructor
    public Landmark() {}

    public Landmark(String name, String description, String location, String category, Float rating) {
        this.landmarkId = generateLandmarkId();
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.rating = rating;
        this.createdAt = new Date();  // Initialize createdAt
        this.updatedAt = new Date();  // Initialize updatedAt
    }

    private String generateLandmarkId() {
        return UUID.randomUUID().toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.landmarkId == null || this.landmarkId.trim().isEmpty()) {
            this.landmarkId = generateLandmarkId();
        }
        this.createdAt = new Date();  // Set createdAt when the entity is first persisted
        this.updatedAt = new Date();  // Initialize updatedAt
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();  // Update only updatedAt when an update occurs
    }

    // Getters and setters
    public String getLandmarkId() { return landmarkId; }
    public void setLandmarkId(String landmarkId) { this.landmarkId = landmarkId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }

    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
}
