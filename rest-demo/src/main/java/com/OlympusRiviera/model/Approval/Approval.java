package com.OlympusRiviera.model.Approval;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Approval")
@Data
public class Approval {
    @Id
    private String approval_id;
    private String approval_type;
    private String entity_id;
    private String entity_type;
    private String user_id;
    private String status;
    private String old_entity_id;
    private String comments;


    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Approval() {
    }

    public Approval(String approval_id, String approval_type, String entity_id, String entity_type, String user_id, String employee_id, String status, Date createdAt, Date updatedAt, String old_entity_id, String comments) {
        this.approval_id = generateId();
        this.approval_type = approval_type;
        this.entity_id = entity_id;
        this.entity_type = entity_type;
        this.user_id = user_id;
        this.status = status;
        this.old_entity_id = old_entity_id;
        this.comments = comments;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }


    private String generateId() {
        return "approv_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }


    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.approval_id == null) {
            this.approval_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }
}