package com.OlympusRiviera.service.Amenity;

import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Destination.Destination;

import java.util.List;

public interface AmenityService {

    public String createAmenity(Amenity amenity);
    public String updateAmenity(Amenity amenity);
    public String deleteAmenity(String amenity_id);
    public Destination getAmenity(String amenity_id);
    public List<Amenity> getAllAmenities();
}
