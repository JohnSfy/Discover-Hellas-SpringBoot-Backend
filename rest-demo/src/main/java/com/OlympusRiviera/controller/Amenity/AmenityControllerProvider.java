package com.OlympusRiviera.controller.Amenity;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Approval.Approval;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Approval.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/provider/amenity")
public class AmenityControllerProvider {
    private final ApprovalService approvalService;  // Declare the service

    private final AmenityService amenityService;

    public AmenityControllerProvider(ApprovalService approvalService, AmenityService amenityService) {
        this.approvalService = approvalService;
        this.amenityService = amenityService;
    }


    //Create request for a amenity entry and waiting potap to approve
    @PostMapping("/add-request/create")
    public ResponseEntity<String> createDestinationDetails(@RequestBody Amenity amenity) {
        amenity.setStatus("PENDING");

        // Save the amenity using the AmenityService (assuming your service handles saving)
        amenityService.createAmenity(amenity);

        // Create a new Approval record
        Approval approval = new Approval();
        approval.setEntity_id(amenity.getAmenity_id()); // Set the amenity ID
        approval.setEntity_type("Amenity"); // Since it's an Amenity
        approval.setApproval_type("Create"); // This is a "Create" approval type
        approval.setStatus("PENDING"); // Status is "PENDING" since it is awaiting approval
        approval.setProvider_id(amenity.getProvider_id()); // Assuming this is available in Amenity
        approval.setEmployee_id("employee_id_here"); // This might come from your session, token, etc.

        // Save the Approval object (assuming ApprovalService has a method to handle saving)
        approvalService.createApproval(approval);

        // Create a success message
        String message = "Amenity with id: " + amenity.getAmenity_id() + " created successfully and pending approval " +"with id "+ approval.getApproval_id();

        // Return a response with the success message
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


//    @PutMapping("/edit-request/create/{amenity_id}")
//    public ResponseEntity<String> updateDestination(@PathVariable String amenity_id, @RequestBody Amenity amenity) {
//        amenity.setAmenity_id(amenity_id);
//        amenity.setStatus("PENDING");
//        amenityService.updateAmenity(amenity);
//        String message = "Amenity with id: " + amenity.getAmenity_id() + " updated successfully and waiting approval from ΠΟΤΑΠ";
//        return ResponseEntity.ok(message); // Return 200 OK
//    }
@PutMapping("/edit-request/create/{amenity_id}")
public ResponseEntity<String> updateOrCreatePendingAmenity(@PathVariable String amenity_id, @RequestBody Amenity updatedAmenity) {
    // Fetch the existing amenity
    Amenity existingAmenity = amenityService.getAmenity(amenity_id);
    if (existingAmenity == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Amenity with id: " + amenity_id + " not found.");
    }

    // Create a new amenity with the updated values
    Amenity newAmenity = new Amenity();
    newAmenity.setName(updatedAmenity.getName());
    newAmenity.setCategory_id(updatedAmenity.getCategory_id());
    newAmenity.setProvider_id(updatedAmenity.getProvider_id());
    newAmenity.setPhone(updatedAmenity.getPhone());
    newAmenity.setEmail(updatedAmenity.getEmail());
    newAmenity.setLatitude(updatedAmenity.getLatitude());
    newAmenity.setLongitude(updatedAmenity.getLongitude());
    newAmenity.setDescription(updatedAmenity.getDescription());
    newAmenity.setPhotos(updatedAmenity.getPhotos());
    newAmenity.setStatus("PENDING"); // Set the new amenity's status to PENDING

    // Save the new amenity
    amenityService.createAmenity(newAmenity);

    // Optionally, create a new approval record for the new amenity
    Approval approval = new Approval();
    approval.setEntity_id(newAmenity.getAmenity_id());
    approval.setOld_entity_id(amenity_id);
    approval.setEntity_type("Amenity");
    approval.setApproval_type("Edit"); // Indicating this is for an edit operation
    approval.setStatus("PENDING");
    approval.setProvider_id(newAmenity.getProvider_id());
    approval.setEmployee_id("employee_id_here"); // Replace as needed
    approvalService.createApproval(approval);

    // Return success message
    String message = "Updated version of Amenity with id: " + amenity_id + " created as new record with id: " + newAmenity.getAmenity_id() + " and status set to 'PENDING'.";
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
}


    @DeleteMapping("/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " deleted successfully from Provider";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
