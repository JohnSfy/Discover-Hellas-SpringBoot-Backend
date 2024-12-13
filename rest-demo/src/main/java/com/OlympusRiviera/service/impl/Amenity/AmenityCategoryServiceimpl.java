package com.OlympusRiviera.service.impl.Amenity;

import com.OlympusRiviera.model.Amenity.AmenityCategory;
import com.OlympusRiviera.repository.Amenity.AmenityCategoryRepository;
import com.OlympusRiviera.service.Amenity.AmenityCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityCategoryServiceimpl implements AmenityCategoryService {

    AmenityCategoryRepository amenityCategoryRepository;

    public AmenityCategoryServiceimpl(AmenityCategoryRepository amenityCategoryRepository) {
        this.amenityCategoryRepository = amenityCategoryRepository;
    }

    public String createAmenityCategory(AmenityCategory amenityCategory) {
        amenityCategoryRepository.save(amenityCategory);
        return "Success";
    }

    @Override
    public String updateAmenityCategory(AmenityCategory amenityCategory) {
        // More logic
        amenityCategoryRepository.save(amenityCategory);
        return "Update Success";
    }

    public String deleteAmenityCategory(String category_id) {
        amenityCategoryRepository.deleteById(category_id);
        return "Deleted Success";
    }

    @Override
    public AmenityCategory getAmenityCategory(String category_id) {
        return amenityCategoryRepository.findById(category_id).get();
    }

    public List<AmenityCategory> getAllAmenityCategories() {
        return amenityCategoryRepository.findAll();
    }
}
