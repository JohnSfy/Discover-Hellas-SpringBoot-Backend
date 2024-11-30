package com.OlympusRiviera.controller.Amenity;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.service.Amenity.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/provider/amenity")
public class AmenityControllerProvider {

    private final AmenityService amenityService;

    public AmenityControllerProvider(AmenityService amenityService) {
        this.amenityService = amenityService;
    }


    //Create request for a amenity entry and waiting potap to approve
    @PostMapping("/add-request/create")
    public ResponseEntity<String> createDestinationDetails(@RequestBody Amenity amenity) {
        amenity.setStatus("PENDING");
        amenityService.createAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " created successfully and pending approval";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }


    @PutMapping("/edit-request/create/{amenity_id}")
    public ResponseEntity<String> updateDestination(@PathVariable String amenity_id, @RequestBody Amenity amenity) {
        amenity.setAmenity_id(amenity_id);
        amenity.setStatus("PENDING");
        amenityService.updateAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " updated successfully and waiting approval from ΠΟΤΑΠ";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @DeleteMapping("/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " deleted successfully from Provider";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
