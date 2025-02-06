package com.DiscoverHellas.service.Amenity;

import com.DiscoverHellas.model.Amenity.Amenity;

import java.util.List;

public interface AmenityService {

    public String createAmenity(Amenity amenity);
    public String updateAmenity(Amenity amenity);
    public String deleteAmenity(String amenity_id);
    public Amenity getAmenity(String amenity_id);
    public List<Amenity> getAllAmenities();
}
