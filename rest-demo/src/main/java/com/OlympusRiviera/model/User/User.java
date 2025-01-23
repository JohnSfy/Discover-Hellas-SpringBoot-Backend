package com.OlympusRiviera.model.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)  // Add this
@Data
public class User {

    @Id
    private String user_id;
    private String googleId;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String photo;
    private String phone;
    private String address;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public User(String user_id, String username, String firstname, String lastname, String email, Role role, String password, String googleId, String photo, String phone, String address) {
        this.user_id = generateId(); //random id for user
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.password = password;
        this.googleId = googleId;
        this.photo = photo;
        this.phone = phone;
        this.address = address;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public User() {
    }


    private String generateId() {
        return "usr_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8); // Prefix with 'destcat'
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        if (this.user_id == null) {
            this.user_id = generateId();
        }
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date(); // Update the updatedAt field
    }


}
