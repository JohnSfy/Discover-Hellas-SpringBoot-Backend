package com.OlympusRiviera.controller.Destination;


import com.OlympusRiviera.model.Destination.DestinationCategory;
import com.OlympusRiviera.service.Destination.DestinationCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/destination/category")
public class DestinationCategoryController {

    private final DestinationCategoryService destinationCategoryService;

    public DestinationCategoryController(DestinationCategoryService destinationCategoryService) {
        this.destinationCategoryService = destinationCategoryService;
    }

    // Get details of a specific destination categories by ID
    @GetMapping("/{category_id}")
    public ResponseEntity<DestinationCategory> getDestinationCategoryDetails(@PathVariable("category_id") String category_id) {
        DestinationCategory destinationCategoryDetails = destinationCategoryService.getDestinationCategory(category_id);
        if (destinationCategoryDetails != null) {
            return ResponseEntity.ok(destinationCategoryDetails); // Return 200 OK with the destination
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the destination doesn't exist
        }
    }

    // Get all destination details
    @GetMapping("/get/all")
    public ResponseEntity<List<DestinationCategory>> getAllDestinationCategoryDetails() {
        List<DestinationCategory> destinationCategories = destinationCategoryService.getAllDestinationCategories();
        return ResponseEntity.ok(destinationCategories); // Return 200 OK with the list of destinations
    }

    // Create a new destination
    @PostMapping("/create")
    public ResponseEntity<String> createDestinationDetails(@RequestBody DestinationCategory destinationCategory) {
        destinationCategoryService.createDestinationCategory(destinationCategory);
        String message = "Destination Category with id: " + destinationCategory.getCategory_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    // Update an existing destination by ID
    @PutMapping("/{category_id}")
    public ResponseEntity<String> updateDestinationCategory(@PathVariable String category_id, @RequestBody DestinationCategory destinationCategory) {
        destinationCategory.setCategory_id(category_id);
        destinationCategoryService.updateDestinationCategory(destinationCategory);
        String message = "Destination Category with id: " + destinationCategory.getCategory_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    // Delete a destination by ID
    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteDestinationCategory(@PathVariable String category_id) {
        destinationCategoryService.deleteDestinationCategory(category_id);
        String message = "Destination Category with id: " + category_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
    }
}
