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
        String message = "Amenity with id: " + amenity.getAmenity_id() + " created successfully and pending approval";

        // Return a response with the success message
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
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
