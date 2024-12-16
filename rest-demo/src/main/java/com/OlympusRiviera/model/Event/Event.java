package com.OlympusRiviera.model.Event;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Event")
@Data
public class Event {

    @Id
    private String event_id;
    private String name;
    private String category_id;
    private String organizer_id;
    private String phone;
    private String email;
    private String status;
    private Date event_start;
    private Date event_end;
    private String latitude;
    private String longitude;
    private String description;
    private String siteLink;
    @Column(columnDefinition = "TEXT") // Allow larger content
    private String photos;

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Event(){};

    public Event(String event_id, String name, String category_id, String organizer_id, String phone, String email, String status, Date event_start, Date event_end, String latitude, String longitude, String description, String photos, String siteLink, Date createdAt, Date updatedAt) {
        this.event_id = generateId();
        this.name = name;
        this.category_id = category_id;
        this.organizer_id = organizer_id;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.event_start = event_start;
        this.event_end = event_end;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photos = photos;
        this.siteLink = siteLink;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    private String generateId() {
        return "even_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.event_id == null) {
            this.event_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }
}
