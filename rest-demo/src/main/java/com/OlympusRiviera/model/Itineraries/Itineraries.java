package com.OlympusRiviera.model.Itineraries;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Itineraries")
@Data
public class Itineraries {

    @Id
    private String itinerary_id;

    @Column(columnDefinition = "JSONB")
    private String plan;
    private String user_id;
    @Column(updatable = false) // Prevent overwriting during updates
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Itineraries(String itinerary_id, String plan, String user_id) {
        this.itinerary_id = itinerary_id;
        this.plan = plan;
        this.user_id = user_id;
    }
}
