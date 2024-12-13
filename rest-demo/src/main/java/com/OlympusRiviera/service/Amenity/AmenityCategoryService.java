package com.OlympusRiviera.service.Amenity;

import com.OlympusRiviera.model.Amenity.AmenityCategory;

import java.util.List;

public interface AmenityCategoryService {
    public String createAmenityCategory(AmenityCategory amenityCategory);
    public String updateAmenityCategory(AmenityCategory amenityCategory);
    public String deleteAmenityCategory(String category_id);
    public AmenityCategory getAmenityCategory(String category_id);
    public List<AmenityCategory> getAllAmenityCategories();
}
