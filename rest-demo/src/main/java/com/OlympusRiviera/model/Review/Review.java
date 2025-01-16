package com.OlympusRiviera.model.Review;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "Review")
@Data
public class Review {

    @Id
    private String review_id;
    private String user_id;
    private String entity_id;
    private String status;
    private String view; // potap selects to show teh review to the public

    @Enumerated(value = EnumType.STRING)
    private Entity_Type entity_type;
    private int rating;
    private String comment;

    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Review() {
    }

    public Review(String review_id, String user_id, String entity_id, Entity_Type entity_type, int rating, String comment, String status, String view) {
        this.review_id = generateId();
        this.user_id = user_id;
        this.entity_id = entity_id;
        this.entity_type = entity_type;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.view = view;
        this.createdAt = new Date();
        this.updatedAt = new Date();

    }

    private String generateId() {
        return "rev_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.review_id == null) {
            this.review_id = generateId();
        }
        this.updatedAt = new Date();
    }
}
