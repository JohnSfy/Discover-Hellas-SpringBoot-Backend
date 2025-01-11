package com.OlympusRiviera.model.User;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
public class User implements UserDetails {

    @Id
    private String user_id;
    private String googleid;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public User(String user_id, String username, String firstname, String lastname, String email, Role role, String password, String googleid) {
        this.user_id = generateId(); //random id for user
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.password = password;
        this.googleid = googleid;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
