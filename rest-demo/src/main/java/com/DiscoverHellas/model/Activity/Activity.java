package com.DiscoverHellas.model.Activity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Activity")
@Data
public class Activity {

    @Id
    private String activity_id;

    private String name;
    private String category_id;
    private String description;
    private String longitude;
    private String latitude;

    @Column(columnDefinition = "TEXT") // Allow larger content
    private String photos;

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Default constructor
    public Activity() {}

    public Activity(String name, String category_id, String description, String latitude, String longitude, String photos) {
        this.activity_id = generateId();
        this.name = name;
        this.category_id = category_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photos = photos;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    private String generateId() {
        return "activ_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.activity_id == null) {
            this.activity_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }
}
