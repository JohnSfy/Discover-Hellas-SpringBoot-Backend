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

//    public RegisteredUser(String hobbies, String preferences) {
//        super();  // Call the parent constructor
//        this.hobbies = hobbies;
//        this.preferences = preferences;
//    }

    public RegisteredUser(String user_id,String username, String firstname, String lastname, String email, Role role, String password, String googleId, String photo, String phone, String address, String hobbies, String preferences) {
        super(user_id, username, firstname, lastname, email, role, password, googleId, photo, phone, address);  // Calls the User constructor
        this.hobbies = hobbies;
        this.preferences = preferences;
    }

//    public RegisteredUser(String hobbies, String preferences) {
//        this.hobbies = hobbies;
//        this.preferences = preferences;
//    }




//    // Convert object to JSON string (serialization)
//    // Helper method to convert objects to JSON strings
//    private String stringify(Object object) {
//        try {
//            return new ObjectMapper().writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to convert object to JSON", e);
//        }
//    }
//
//    // Helper method to convert JSON string back to the original object
//    public <T> T getAsObject(String jsonString, Class<T> valueType) {
//        try {
//            return new ObjectMapper().readValue(jsonString, valueType);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to parse JSON string", e);
//        }
//    }

    @JsonProperty
    public String getHobbies() {
        return hobbies;
    }

    @JsonProperty
    public String getPreferences() {
        return preferences;
    }
}

