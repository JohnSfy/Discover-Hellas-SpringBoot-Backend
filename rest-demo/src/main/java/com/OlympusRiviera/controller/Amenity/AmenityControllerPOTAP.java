package com.OlympusRiviera.controller.Amenity;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.service.Amenity.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/amenity")
public class AmenityControllerPOTAP {

    private final AmenityService amenityService;

    public AmenityControllerPOTAP(AmenityService amenityService) {
        this.amenityService = amenityService;
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


}
