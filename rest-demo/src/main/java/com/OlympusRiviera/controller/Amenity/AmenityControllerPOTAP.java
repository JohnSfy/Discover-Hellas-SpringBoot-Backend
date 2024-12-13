package com.OlympusRiviera.controller.Amenity;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Amenity.AmenityCategory;
import com.OlympusRiviera.service.Amenity.AmenityCategoryService;
import com.OlympusRiviera.service.Amenity.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/amenity")
public class AmenityControllerPOTAP {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    public AmenityControllerPOTAP(AmenityService amenityService, AmenityCategoryService amenityCategoryService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
    }

    //POTAP TO CREATE AMENITY WITH STATUS APPROVED FROM THE BEGGINING
    @GetMapping("/get/all")
    public ResponseEntity<List<Amenity>> getAllAmenityDetails() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities); // Return 200 OK with the list of destinations
    }

    @PutMapping("/edit/{amenity_id}")
    public ResponseEntity<String> updateDAmenity(@PathVariable String amenity_id, @RequestBody Amenity amenity) {
        amenity.setAmenity_id(amenity_id);
        amenity.setStatus("APPROVED"); //Because ΠΟΤΑΠ UPDATED the amenity
        amenityService.updateAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " Deleted Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Use 200 OK instead
    }


    //-----------------------Amenity Category Control-----------------------------------

    @GetMapping("/category/get/all")
    public ResponseEntity<List<AmenityCategory>> getAllAmenityCategoryDetails() {
        List<AmenityCategory> amenityCategories = amenityCategoryService.getAllAmenityCategories();
        return ResponseEntity.ok(amenityCategories);
    }

    @PostMapping("/category/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody AmenityCategory amenityCategory) {
        amenityCategoryService.createAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PutMapping("/category/{category_id}")
    public ResponseEntity<String> updateAmenityCategory(@PathVariable String category_id, @RequestBody AmenityCategory amenityCategory) {
        amenityCategory.setCategory_id(category_id);
        amenityCategoryService.updateAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<String> deleteAmenityCategory(@PathVariable String category_id) {
        amenityCategoryService.deleteAmenityCategory(category_id);
        String message = "Amenity Category with id: " + category_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
    }

}
