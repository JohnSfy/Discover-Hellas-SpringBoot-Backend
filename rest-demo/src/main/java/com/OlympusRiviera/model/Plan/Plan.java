package com.OlympusRiviera.model.Plan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Plan")
@Data
public class Plan {

    @Id
    private String plan_id;

    private String title;

    // Store the JSON as a String
    private String plan;

    private String user_id;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Plan() {}

    public Plan(String title, Object plan, String user_id) {
        this.plan_id = generateId();
        this.title = title;
        this.plan = stringifyPlan(plan);
        this.user_id = user_id;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.plan_id == null) {
            this.plan_id = generateId();
        }
        this.updatedAt = new Date();
    }

    private String generateId() {
        return "pln_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    // Convert object to JSON string
    private String stringifyPlan(Object plan) {
        try {
            return new ObjectMapper().writeValueAsString(plan);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert plan to JSON", e);
        }
    }

    // Convert JSON string back to object
    public <T> T getPlanAsObject(Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(this.plan, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON plan", e);
        }
    }

    // Set plan by serializing to JSON
    public void setPlan(Object plan) {
        this.plan = stringifyPlan(plan);
    }


}
