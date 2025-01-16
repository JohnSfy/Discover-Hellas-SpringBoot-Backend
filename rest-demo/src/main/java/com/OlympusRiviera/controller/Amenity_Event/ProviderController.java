package com.OlympusRiviera.controller.Amenity_Event;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Approval.Approval;
import com.OlympusRiviera.model.Event.Event;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Approval.ApprovalService;
import com.OlympusRiviera.service.Event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://olumpus-riviera-frontend.vercel.app")
@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private final ApprovalService approvalService;  // Declare the service
    private final AmenityService amenityService;
    private final EventService eventService;

    public ProviderController(ApprovalService approvalService, AmenityService amenityService, EventService eventService) {
        this.approvalService = approvalService;
        this.amenityService = amenityService;
        this.eventService = eventService;
    }


    //--------------------------------Event Controller----------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------
    //Create request for a event entry and waiting potap to approve

    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @PostMapping("/event/add-request/create")
        public ResponseEntity<String> createEventDetails(@RequestBody Event event) {
        event.setStatus("PENDING");

        // Save the amenity using the AmenityService (assuming your service handles saving)
        eventService.createEvent(event);

        // Create a new Approval record
        Approval approval = new Approval();
        approval.setEntity_id(event.getEvent_id()); // Set the amenity ID
        approval.setEntity_type("Event"); // Since it's an Amenity
        approval.setApproval_type("Create"); // This is a "Create" approval type
        approval.setStatus("PENDING"); // Status is "PENDING" since it is awaiting approval
        approval.setProvider_id(event.getOrganizer_id()); // Assuming this is available in Amenity
        approval.setEmployee_id("employee_id_here"); // This might come from your session, token, etc.

        // Save the Approval object (assuming ApprovalService has a method to handle saving)
        approvalService.createApproval(approval);

        // Create a success message
        String message = "Event with id: " + event.getEvent_id() + " created successfully and pending approval " +"with id "+ approval.getApproval_id();

        // Return a response with the success message
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @PutMapping("/event/edit-request/create/{event_id}")
    public ResponseEntity<String> updateOrCreatePendingEvent(@PathVariable String event_id, @RequestBody Event updatedEvent) {
        // Fetch the existing event
        Event existingEvent = eventService.getEvent(event_id);
        if (existingEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event with id: " + event_id + " not found.");
        }

        // Create a new event with merged values
        Event newEvent = new Event();
        newEvent.setName(updatedEvent.getName() != null ? updatedEvent.getName() : existingEvent.getName());
        newEvent.setCategory_id(updatedEvent.getCategory_id() != null ? updatedEvent.getCategory_id() : existingEvent.getCategory_id());
        newEvent.setOrganizer_id(updatedEvent.getOrganizer_id() != null ? updatedEvent.getOrganizer_id() : existingEvent.getOrganizer_id());
        newEvent.setPhone(updatedEvent.getPhone() != null ? updatedEvent.getPhone() : existingEvent.getPhone());
        newEvent.setEmail(updatedEvent.getEmail() != null ? updatedEvent.getEmail() : existingEvent.getEmail());
        newEvent.setEvent_start(updatedEvent.getEvent_start() != null ? updatedEvent.getEvent_start() : existingEvent.getEvent_start());
        newEvent.setEvent_end(updatedEvent.getEvent_end() != null ? updatedEvent.getEvent_end() : existingEvent.getEvent_end());
        newEvent.setLatitude(updatedEvent.getLatitude() != null ? updatedEvent.getLatitude() : existingEvent.getLatitude());
        newEvent.setLongitude(updatedEvent.getLongitude() != null ? updatedEvent.getLongitude() : existingEvent.getLongitude());
        newEvent.setDescription(updatedEvent.getDescription() != null ? updatedEvent.getDescription() : existingEvent.getDescription());
        newEvent.setPhotos(updatedEvent.getPhotos() != null ? updatedEvent.getPhotos() : existingEvent.getPhotos());
        newEvent.setSiteLink(updatedEvent.getSiteLink() != null ? updatedEvent.getSiteLink() : existingEvent.getSiteLink());
        newEvent.setStatus("PENDING"); // Set the new event's status to PENDING

        // Save the new event
        eventService.createEvent(newEvent);

        // Optionally, create a new approval record for the new event
        Approval approval = new Approval();
        approval.setEntity_id(newEvent.getEvent_id());
        approval.setOld_entity_id(event_id);
        approval.setEntity_type("Event");
        approval.setApproval_type("Edit"); // Indicating this is for an edit operation
        approval.setStatus("PENDING");
        approval.setProvider_id(newEvent.getOrganizer_id());
        approval.setEmployee_id("employee_id_here"); // Replace as needed
        approvalService.createApproval(approval);

        // Return success message
        String message = "Updated version of Event with id: " + event_id + " created as new record with id: " + newEvent.getEvent_id() + " and status set to 'PENDING'.";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @DeleteMapping("/event/delete/{event_id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String event_id) {
        eventService.deleteEvent(event_id);
        String message = "Event with id: " + event_id + " deleted successfully from Provider";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }




    //---------------------------------Amenity Controller--------------------------------------------------

    //Create request for a amenity entry and waiting potap to approve
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @PostMapping("/amenity/add-request/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody Amenity amenity) {
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

    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @PutMapping("/amenity/edit-request/create/{amenity_id}")
    public ResponseEntity<String> updateOrCreatePendingAmenity(@PathVariable String amenity_id, @RequestBody Amenity updatedAmenity) {
        // Fetch the existing amenity
        Amenity existingAmenity = amenityService.getAmenity(amenity_id);
        if (existingAmenity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Amenity with id: " + amenity_id + " not found.");
        }

        // Create a new amenity with merged values
        Amenity newAmenity = new Amenity();
        newAmenity.setName(updatedAmenity.getName() != null ? updatedAmenity.getName() : existingAmenity.getName());
        newAmenity.setCategory_id(updatedAmenity.getCategory_id() != null ? updatedAmenity.getCategory_id() : existingAmenity.getCategory_id());
        newAmenity.setProvider_id(updatedAmenity.getProvider_id() != null ? updatedAmenity.getProvider_id() : existingAmenity.getProvider_id());
        newAmenity.setPhone(updatedAmenity.getPhone() != null ? updatedAmenity.getPhone() : existingAmenity.getPhone());
        newAmenity.setEmail(updatedAmenity.getEmail() != null ? updatedAmenity.getEmail() : existingAmenity.getEmail());
        newAmenity.setLatitude(updatedAmenity.getLatitude() != null ? updatedAmenity.getLatitude() : existingAmenity.getLatitude());
        newAmenity.setLongitude(updatedAmenity.getLongitude() != null ? updatedAmenity.getLongitude() : existingAmenity.getLongitude());
        newAmenity.setDescription(updatedAmenity.getDescription() != null ? updatedAmenity.getDescription() : existingAmenity.getDescription());
        newAmenity.setPhotos(updatedAmenity.getPhotos() != null ? updatedAmenity.getPhotos() : existingAmenity.getPhotos());
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


    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    @DeleteMapping("/amenity/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " deleted successfully from Provider";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
