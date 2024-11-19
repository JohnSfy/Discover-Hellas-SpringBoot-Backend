package com.OlympusRiviera.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "destination")
public class destination {

    @Id
    private String destination_id;

    private String name;
    private String category_id;
    private String description;

    @Embedded
    private DestinationLocation location;  // Location as an embedded object

    @ElementCollection
    private List<String> photos; // List of photo URLs

    private String link_360_view;

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Default constructor
    public destination() {}

    public destination(String destination_id, String name, String category_id, String description, DestinationLocation location, List<String> photos, String link_360_view, Date createdAt, Date updatedAt) {
        this.destination_id = generateDestinationId();
        this.name = name;
        this.category_id = category_id;
        this.location = location;
        this.description = description;
        this.photos = photos;
        this.link_360_view = link_360_view;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    private String generateDestinationId() {
        return UUID.randomUUID().toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.destination_id == null) {
            this.destination_id = generateDestinationId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }

    // Getters and setters
    public String getDestination_id() { return destination_id; }
    public void setDestination_id(String destination_id) { this.destination_id = destination_id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DestinationLocation getLocation() {
        return location;
    }

    public void setLocation(DestinationLocation location) {
        this.location = location;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getLink_360_view() {
        return link_360_view;
    }

    public void setLink_360_view(String link_360_view) {
        this.link_360_view = link_360_view;
    }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) {
        if (this.createdAt == null) { // Only allow setting once
            this.createdAt = createdAt;
        }
    }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
