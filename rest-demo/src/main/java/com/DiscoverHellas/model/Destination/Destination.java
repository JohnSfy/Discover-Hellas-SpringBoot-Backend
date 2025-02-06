package com.DiscoverHellas.model.Destination;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Destination")
@Data
public class Destination {

    @Id
    private String destination_id;

    private String name;
    private String category_id;
    private String description;
    private String longitude;
    private String latitude;

    @Column(columnDefinition = "TEXT") // Allow larger content
    private String photos;

    private String link_360_view;

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Default constructor
    public Destination() {}

    public Destination(String name, String category_id, String description, String latitude, String longitude, String photos, String link_360_view) {
        this.destination_id = generateId();
        this.name = name;
        this.category_id = category_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photos = photos;
        this.link_360_view = link_360_view;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    private String generateId() {
        return "dest_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.destination_id == null) {
            this.destination_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }
}
