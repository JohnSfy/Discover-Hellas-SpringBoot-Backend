package com.OlympusRiviera.controller.Approval;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Approval.Approval;
import com.OlympusRiviera.model.Event.Event;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Approval.ApprovalService;
import com.OlympusRiviera.service.Event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://olumpus-riviera-frontend.vercel.app")
@RestController
@RequestMapping("/api")
public class ApprovalController {

    private final ApprovalService approvalService;
    private final AmenityService amenityService;
    private final EventService eventService;

    public ApprovalController(ApprovalService approvalService, AmenityService amenityService, EventService eventService) {
        this.approvalService = approvalService;
        this.amenityService = amenityService;
        this.eventService = eventService;
    }

    // -----------------Amenity-------------------------------


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

    //Get all Update Requests
    @GetMapping("/admin/approval/amenity/edit-request/get/all")
    public ResponseEntity<Object> getAllUpdateAmenityApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Event", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Edit".equals(approval.getApproval_type()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no approvals are found with a custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Amenity approvals found with status 'PENDING' and approval type 'Update'.");
        } else {
            // Return 200 OK with the filtered list of Event approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Get a specific Create request
    @GetMapping("/admin/approval/amenity/add-request/get/{amenity_id}")
    public ResponseEntity<Object> getCreateAmenityApprovalDetails(@PathVariable("amenity_id") String amenity_id) {
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

    //Get a specific Update Request
    @GetMapping("/admin/approval/amenity/edit-request/get/{amenity_id}")
    public ResponseEntity<Object> getUpdateAmenityApprovalDetails(@PathVariable("amenity_id") String amenity_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by amenity_id, entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Edit".equals(approval.getApproval_type())
                        && amenity_id.equals(approval.getEntity_id()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Amenity approvals found with amenity ID: " + amenity_id
                            + ", status 'PENDING', and approval type 'Edit'.");
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Update the status of Create Request
    @PutMapping("/admin/approval/amenity/add-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateCreateAmenityApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {
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

    //Update status of Edit request
    @PutMapping("/admin/approval/amenity/edit-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateEditAmenityApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {
        // Fetch the approval request by amenity_id
        Approval approval = approvalService.getApproval(approval_id);
        Amenity amenity =  amenityService.getAmenity(approval.getEntity_id());

        if (approval == null) {
            // Return 404 Not Found if the approval request does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Approval request not found for ID: " + approval_id);
        }

        // Check if the request is pending and for a "Create" operation
        if (!"PENDING".equals(approval.getStatus()) || !"Edit".equals(approval.getApproval_type())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot update status for non-pending or non-edit requests.");
        }

        // Update the status of the approval request
        approval.setStatus(status); // Status should be either "ACCEPTED" or "REJECTED"
        amenity.setStatus(status);
        approvalService.updateApproval(approval);
        amenityService.updateAmenity(amenity);

        //Se
        if("APPROVED".equals(status)){
            amenityService.deleteAmenity(approval.getOld_entity_id());
        }

        // Return success message
        return ResponseEntity.ok("Approval request for amenity ID: " + approval.getEntity_id() + " has been updated to status: " + status);
    }


    //---------------------------Event-------------------------------------
    //----------------------------------------------------------------


    //Ποταπ getting all pending Create amenity requests
    @GetMapping("/admin/approval/event/add-request/get/all")
    public ResponseEntity<Object> getAllCreateEventApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
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
            // Return 200 OK with the filtered list of Amenity approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Get all Update Requests
    @GetMapping("/admin/approval/event/edit-request/get/all")
    public ResponseEntity<Object> getAllUpdateEventApprovals() {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by entity_type = "Event", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Edit".equals(approval.getApproval_type()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no approvals are found with a custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Event approvals found with status 'PENDING' and approval type 'Edit'.");
        } else {
            // Return 200 OK with the filtered list of Event approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Get a specific Create request
    @GetMapping("/admin/approval/event/add-request/get/{event_id}")
    public ResponseEntity<Object> getCreateEventApprovalDetails(@PathVariable("event_id") String event_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by event_id, entity_type = "Event", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Create".equals(approval.getApproval_type())
                        && event_id.equals(approval.getEntity_id()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Event approvals found with Event ID: " + event_id
                            + ", status 'PENDING', and approval type 'Create'.");
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Get a specific Update Request
    @GetMapping("/admin/approval/event/edit-request/get/{event_id}")
    public ResponseEntity<Object> getUpdateEventApprovalDetails(@PathVariable("event_id") String event_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by amenity_id, entity_type = "Amenity", status = "PENDING", and approval_type = "Create"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type())
                        && "PENDING".equals(approval.getStatus())
                        && "Edit".equals(approval.getApproval_type())
                        && event_id.equals(approval.getEntity_id()))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching Event approvals found with Event ID: " + event_id
                            + ", status 'PENDING', and approval type 'Edit'.");
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    //Update the status of Create Request
    @PutMapping("/admin/approval/event/add-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateCreateEventApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {

        Approval approval = approvalService.getApproval(approval_id);
        Event event =  eventService.getEvent(approval.getEntity_id());

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
        event.setStatus(status);
        approvalService.updateApproval(approval);
        eventService.updateEvent(event);
        // Return success message
        return ResponseEntity.ok("Approval request for Event ID: " + approval.getEntity_id() + " has been updated to status: " + status);
    }

    //Update status of Edit request
    @PutMapping("/admin/approval/event/edit-request/get/{approval_id}/updateStatus") // WITH ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateEditEventApprovalStatus(@PathVariable("approval_id") String approval_id, @RequestParam("status") String status) {
        // Fetch the approval request by amenity_id
        Approval approval = approvalService.getApproval(approval_id);
        Event event =  eventService.getEvent(approval.getEntity_id());

        if (approval == null) {
            // Return 404 Not Found if the approval request does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Approval request not found for ID: " + approval_id);
        }

        // Check if the request is pending and for a "Create" operation
        if (!"PENDING".equals(approval.getStatus()) || !"Edit".equals(approval.getApproval_type())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot update status for non-pending or non-edit requests.");
        }

        // Update the status of the approval request
        approval.setStatus(status); // Status should be either "ACCEPTED" or "REJECTED"
        event.setStatus(status);
        approvalService.updateApproval(approval);
        eventService.updateEvent(event);

        //Se
        if("APPROVED".equals(status)){
            eventService.deleteEvent(approval.getOld_entity_id());
        }

        // Return success message
        return ResponseEntity.ok("Approval request for Event ID: " + approval.getEntity_id() + " has been updated to status: " + status);
    }



    //-----------------------------------Provider-----------------------------------------------------
    //------------------------------------------------------------------------------------------------

    //-----------------------------------Event--------------------------------------------------------

    // Get a pending approval to Edit it again?!
// If this returns 200, you can understand that the event with id event_id is Edited and pending for approval
    @GetMapping("/provider/approval/event/get/{event_id}")
    public ResponseEntity<Object> getApprovalByEventId(@PathVariable("event_id") String event_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals for either "Create" (entity_id) or "Update" (old_entity_id) operations with entity_type "Event"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type()) // Ensure entity_type is "Event"
                        && "PENDING".equals(approval.getStatus()) // Match pending status
                        && (
                        (approval.getOld_entity_id() == null || approval.getOld_entity_id().isEmpty())
                                ? event_id.equals(approval.getEntity_id()) // Match entity_id for "Create"
                                : event_id.equals(approval.getOld_entity_id()) // Match old_entity_id for "Update"
                ))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching pending approvals found for event with id: " + event_id);
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    // Get all approvals (approved/pending/rejected) from a specific provider with ?provider_id=id
    @GetMapping("/provider/approval/event/get/all")
    public ResponseEntity<Object> getAllEventApprovalsForProvider(@RequestParam("provider_id") String provider_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by provider_id and entity_type "Event"
        List<Approval> providerApprovals = allApprovals.stream()
                .filter(approval -> "Event".equals(approval.getEntity_type()) // Ensure entity_type is "Event"
                        && provider_id.equals(approval.getProvider_id())) // Match provider_id
                .collect(Collectors.toList());

        if (providerApprovals.isEmpty()) {
            // Return 404 if no approvals are found for the provider
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No approvals found for provider with id: " + provider_id);
        } else {
            // Return 200 OK with the filtered list of approvals
            return ResponseEntity.ok(providerApprovals);
        }
    }


    //------------------------------------------Amenities---------------------------------------

    // Get a pending approval to Edit it again?!
// If this returns 200, you can understand that the amenity with id amenity_id is Edited and pending for approval
    @GetMapping("/provider/approval/amenity/get/{amenity_id}")
    public ResponseEntity<Object> getApprovalByAmenityId(@PathVariable("amenity_id") String amenity_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals for either "Create" (entity_id) or "Update" (old_entity_id) operations with entity_type "Amenity"
        List<Approval> matchingApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type()) // Ensure entity_type is "Amenity"
                        && "PENDING".equals(approval.getStatus()) // Match pending status
                        && (
                        (approval.getOld_entity_id() == null || approval.getOld_entity_id().isEmpty())
                                ? amenity_id.equals(approval.getEntity_id()) // Match entity_id for "Create"
                                : amenity_id.equals(approval.getOld_entity_id()) // Match old_entity_id for "Update"
                ))
                .collect(Collectors.toList());

        if (matchingApprovals.isEmpty()) {
            // Return 404 if no matching approvals are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No matching pending approvals found for amenity with id: " + amenity_id);
        } else {
            // Return 200 OK with the filtered list of matching approvals
            return ResponseEntity.ok(matchingApprovals);
        }
    }

    // Get all approvals (approved/pending/rejected) from a specific provider with ?provider_id=id
    @GetMapping("/provider/approval/amenity/get/all")
    public ResponseEntity<Object> getAllAmenitiesApprovalsForProvider(@RequestParam("provider_id") String provider_id) {
        // Fetch all approvals
        List<Approval> allApprovals = approvalService.getAllApprovals();

        // Filter approvals by provider_id and entity_type "Amenity"
        List<Approval> providerApprovals = allApprovals.stream()
                .filter(approval -> "Amenity".equals(approval.getEntity_type()) // Ensure entity_type is "Amenity"
                        && provider_id.equals(approval.getProvider_id())) // Match provider_id
                .collect(Collectors.toList());

        if (providerApprovals.isEmpty()) {
            // Return 404 if no approvals are found for the provider
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No approvals found for provider with id: " + provider_id);
        } else {
            // Return 200 OK with the filtered list of approvals
            return ResponseEntity.ok(providerApprovals);
        }
    }










}
