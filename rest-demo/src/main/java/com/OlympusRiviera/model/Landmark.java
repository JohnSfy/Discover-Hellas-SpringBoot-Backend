package com.OlympusRiviera.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // No-argument constructor
    public Landmark() {}

    public Landmark(String name, String description, String location, String category, Float rating) {
        this.landmarkId = generateLandmarkId();  // Generate UUID for landmarkId
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.rating = rating;
    }

    private String generateLandmarkId() {
        return UUID.randomUUID().toString();  // Generate a random UUID
    }

    // @PrePersist ensures createdAt and updatedAt are set before the entity is saved to the database
    @PrePersist
    public void prePersist() {
        if (this.landmarkId == null) {
            this.landmarkId = generateLandmarkId();  // Generate ID if not provided
        }

        if (this.createdAt == null) {
            this.createdAt = new Date();  // Set createdAt only when it's not already set (on first persist)
        }

        if (this.updatedAt == null) {
            this.updatedAt = new Date();  // Initialize updatedAt on first save
        }
    }

    // @PreUpdate ensures updatedAt is only modified on updates
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();  // Update only updatedAt on update
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
