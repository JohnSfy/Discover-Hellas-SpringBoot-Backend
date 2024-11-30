package com.OlympusRiviera.controller.Approval;


import com.OlympusRiviera.model.Approval.Approval;
import com.OlympusRiviera.service.Approval.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // -----------------ΠΟΤΑΠ-------------------------------


    //Ποταπ getting all pending Create amenity requests
    @GetMapping("/admin/approval/amenity/add-request/get/all")
    public ResponseEntity<Object> getAllAmenityApprovals() {
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
