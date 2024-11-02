package com.OlympusRiviera.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="User")
public class User {

    @Id
    private String userId;
    private String username;
    private String email;
    private String role;
    private String password;

    public User(String userId, String username, String email, String role, String password) {
        this.userId = generateUserId(); //random id for user
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User() {
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }

    //The @PrePersist annotation in JPA is used to mark a method
    // that should be executed before an entity is initially saved
    // (persisted) to the database. This is part of the JPA entity
    // lifecycle and allows you to initialize or modify certain fields
    // automatically when an entity is about to be persisted.
    @PrePersist
    public void prePersist() {
        if (this.userId == null || this.userId.trim().isEmpty()) {
            this.userId = generateUserId();
        }
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    //Created at stamp time
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //Updated at stamp time
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
