package com.OlympusRiviera.model.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "registered_user")
@Data
public class RegisteredUser extends User{


    @Column(columnDefinition = "TEXT")
    @JsonRawValue
    private String hobbies;

    @Column(columnDefinition = "TEXT")
    @JsonRawValue
    private String preferences;


    public RegisteredUser() {}


    public RegisteredUser(String username, String firstname, String lastname, String email, Role role, String password, String googleId, String photo, String phone, String address, String hobbies, String preferences) {
        super(username, firstname, lastname, email, role, password, googleId, photo, phone, address);  // Calls the User constructor
        this.hobbies = hobbies;
        this.preferences = preferences;
    }


    @JsonProperty
    public String getHobbies() {
        return hobbies;
    }

    @JsonProperty
    public String getPreferences() {
        return preferences;
    }

}

