package com.OlympusRiviera.model.Amenity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Amenity")
@Data
public class Amenity {

    private String amenity_id;
    private String name;
    private String category_id;
    private String provider_id;
    private String phone;
    private String email;
    private String status;
    private String latitude;
    private String longitude;
    private String description;

    @Column(columnDefinition = "TEXT") // Allow larger content
    private String photos;
    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Amenity() {};

    public Amenity(String amenity_id, String name, String category_id, String provider_id, String phone, String email, String status, String latitude, String longitude, String description, String photos, Date createdAt, Date updatedAt) {
        this.amenity_id = generateId();
        this.name = name;
        this.category_id = category_id;
        this.provider_id = provider_id;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photos = photos;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }



    private String generateId() {
        return "amen_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }


    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.amenity_id == null) {
            this.amenity_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }
}
