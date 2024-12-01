package com.OlympusRiviera.controller.Approval;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Approval.Approval;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Approval.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ApprovalController {

    private final ApprovalService approvalService;
    private final AmenityService amenityService;

    public ApprovalController(ApprovalService approvalService, AmenityService amenityService) {

        this.approvalService = approvalService;
        this.amenityService = amenityService;
    }

    // -----------------ΠΟΤΑΠ-------------------------------


    //Ποταπ getting all pending Create amenity requests
    @GetMapping("/admin/approval/amenity/add-request/get/all")
    public ResponseEntity<Object> getAllCreateAmenityApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Create".equals(approval.getApproval_type()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no approvals are found with a custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Amenity approvals found with status 'PENDING' and approval type 'Create'.");
        } else {
            // Return 200 OK with the filtered list of Amenity approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    @GetMapping("/admin/approval/amenity/edit-request/get/all")
    public ResponseEntity<Object> getAllUpdateApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Event", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Update".equals(approval.getApproval_type()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no approvals are found with a custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Event approvals found with status 'PENDING' and approval type 'Update'.");
        } else {
            // Return 200 OK with the filtered list of Event approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    @GetMapping("/admin/approval/amenity/add-request/get/{amenity_id}")
    public ResponseEntity<Object> getCreateApprovalDetails(@PathVariable("amenity_id") String amenity_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by amenity_id, entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Create".equals(approval.getApproval_type())
                        && amenity_id.equals(approval.getEntity_id()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Amenity approvals found with amenity ID: " + amenity_id
                            + ", status 'PENDING', and approval type 'Create'.");
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    @GetMapping("/admin/approval/amenity/edit-request/get/{amenity_id}")
    public ResponseEntity<Object> getUpdateApprovalDetails(@PathVariable("amenity_id") String amenity_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by amenity_id, entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Update".equals(approval.getApproval_type())
                        && amenity_id.equals(approval.getEntity_id()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Amenity approvals found with amenity ID: " + amenity_id
                            + ", status 'PENDING', and approval type 'Update'.");
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    @PutMapping("/admin/approval/amenity/add-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateCreateApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {
        // Fetch the approval request by amenity_id
        Approval approval = approvalService.getApproval(approval_id);
        Amenity amenity =  amenityService.getAmenity(approval.getEntity_id());

        if (approval == null) {
            // Return 404 Not Found if the approval request does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Approval request not found for ID: " + approval_id);
        }

        // Check if the request is pending and for a "Create" operation
        if (!"PENDING".equals(approval.getStatus()) || !"Create".equals(approval.getApproval_type())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot update status for non-pending or non-create requests.");
        }

        // Update the status of the approval request
        approval.setStatus(status); // Status should be either "ACCEPTED" or "REJECTED"
        amenity.setStatus(status);
        approvalService.updateApproval(approval);
        amenityService.updateAmenity(amenity);
        // Return success message
        return ResponseEntity.ok("Approval request for amenity ID: " + approval.getEntity_id() + " has been updated to status: " + status);
    }

    @PutMapping("/admin/approval/amenity/edit-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateEditApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {
        // Fetch the approval request by amenity_id
        Approval approval = approvalService.getApproval(approval_id);

        if (approval == null) {
            // Return 404 Not Found if the approval request does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Approval request not found for ID: " + approval_id);
        }

        // Check if the request is pending and for a "Create" operation
        if (!"PENDING".equals(approval.getStatus()) || !"Update".equals(approval.getApproval_type())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot update status for non-pending or non-create requests.");
        }

        // Update the status of the approval request
        approval.setStatus(status); // Status should be either "ACCEPTED" or "REJECTED"
        approvalService.updateApproval(approval);

        // Return success message
        return ResponseEntity.ok("Approval request for amenity ID: " + approval.getEntity_id() + " has been updated to status: " + status);
    }
    //----------------------------------------------------------------
    //----------------------------------------------------------------
    //----------------------------------------------------------------

    //Ποταπ getting all pending Create event requests
    @GetMapping("/admin/approval/event/add-request/get/all")
    public ResponseEntity<Object> getAllEventApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Event", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Create".equals(approval.getApproval_type()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no approvals are found with a custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Event approvals found with status 'PENDING' and approval type 'Create'.");
        } else {
            // Return 200 OK with the filtered list of Event approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }







}
