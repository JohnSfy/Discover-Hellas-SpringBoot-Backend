package com.OlympusRiviera.model.Visit;


import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="visits")
@Data
public class Visit {

    @Id
    private String visit_relationship_id;
    private String user_id;

    @Column(columnDefinition = "TEXT")
    @JsonRawValue
    private String visits;
    //Example JSON format for visits:
    //{
    //  "user_id": "12345",
    //  "visits": [
    //    {
    //      "entity_id": "destination_001"
    //    },
    //    {
    //      "entity_id": "activity_002"
    //    },
    //    {
    //      "entity_id": "destination_003"
    //    }
    //  ]
    //}

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Visit(String user_id, String visits) {
        this.visit_relationship_id = generateId();
        this.user_id = user_id;
        this.visits = visits;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Visit(){};


    private String generateId() {
        return "vstrel_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.visit_relationship_id == null) {
            this.visit_relationship_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }

}
