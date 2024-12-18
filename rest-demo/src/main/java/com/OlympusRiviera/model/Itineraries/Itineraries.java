package com.OlympusRiviera.model.Itineraries;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Itineraries")
@Data
public class Itineraries {

    @Id
    private String itinerary_id;

    @Column(columnDefinition = "TEXT") // Allow larger content
    private String plan;

}
