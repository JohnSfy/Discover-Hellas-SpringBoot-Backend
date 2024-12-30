package com.OlympusRiviera.controller;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Amenity.AmenityCategory;
import com.OlympusRiviera.model.Event.Event;
import com.OlympusRiviera.service.Amenity.AmenityCategoryService;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Event.EventService;
//import com.OlympusRiviera.service.Plan.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class GeneralController {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    private final EventService eventService;
//    private final PlanService planService;

    public GeneralController(AmenityService amenityService, AmenityCategoryService amenityCategoryService, EventService eventService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
        this.eventService = eventService;
//        this.planService = planService;
    }

    //-----------------------Amenity--------------------------------------------------

    @GetMapping("/amenity/get/all")
    public ResponseEntity<List<Amenity>> getAllApprovedAmenities() {
        // Fetch all amenities
        List<Amenity> allAmenities = amenityService.getAllAmenities();

        // Filter the list to include only those with status "APPROVED"
        List<Amenity> approvedAmenities = allAmenities.stream()
                .filter(amenity -> "APPROVED".equalsIgnoreCase(amenity.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(approvedAmenities); // Return 200 OK with the filtered list
    }

    // Get details of a specific amenity by ID
    @GetMapping("/amenity/get/{amenity_id}")
    public ResponseEntity<Amenity> getAmenityDetails(@PathVariable("amenity_id") String amenity_id) {
        Amenity amenityDetails = amenityService.getAmenity(amenity_id);
        if (amenityDetails != null) {
            return ResponseEntity.ok(amenityDetails); // Return 200 OK with the amenity
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the amenity doesn't exist
        }
    }

    //get all amenities from specific category id
    @GetMapping("/amenity/{category_id}/amenities")
    public ResponseEntity<?> getAmenitiesByCategory(@PathVariable("category_id") String category_id) {
        // Fetch all destinations
        List<Amenity> allAmenities = amenityService.getAllAmenities();

        // Filter amenities by category_id
        List<Amenity> filteredAmenities = allAmenities.stream()
                .filter(amenity -> category_id.equals(amenity.getCategory_id()))
                .collect(Collectors.toList());

        if (filteredAmenities.isEmpty()) {
            // Return 404 with a custom message if no amenity are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Amenities found for category ID: " + category_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredAmenities);
        }
    }


    //----------------------Amenity Categories---------------------------------
    @GetMapping("/amenity/category/get/all")
    public ResponseEntity<List<AmenityCategory>> getAllAmenityCategoryDetails() {
        List<AmenityCategory> amenityCategories = amenityCategoryService.getAllAmenityCategories();
        return ResponseEntity.ok(amenityCategories);
    }



    //-------------------------Event-----------------------------------------------
    //-----------------------------------------------------------------------------


    @GetMapping("/event/get/all")
    public ResponseEntity<List<Event>> getAllApprovedEvents() {
        // Fetch all amenities
        List<Event> allEvents = eventService.getAllEvents();

        // Filter the list to include only those with status "APPROVED"
        List<Event> approvedEvents = allEvents.stream()
                .filter(event -> "APPROVED".equalsIgnoreCase(event.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(approvedEvents); // Return 200 OK with the filtered list
    }

    // Get details of a specific event by ID
    @GetMapping("/event/get/{event_id}")
        public ResponseEntity<Event> getEventDetails(@PathVariable("event_id") String event_id) {
        Event eventDetails = eventService.getEvent(event_id);
        if (eventDetails != null) {
            return ResponseEntity.ok(eventDetails); // Return 200 OK with the event
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the event doesn't exist
        }
    }

    //get all Events from specific category id
    @GetMapping("/event/{category_id}/events")
    public ResponseEntity<?> getEventsByCategory(@PathVariable("category_id") String category_id) {
        // Fetch all destinations
        List<Event> allEvents = eventService.getAllEvents();

        // Filter events by category_id
        List<Event> filteredEvents = allEvents.stream()
                .filter(event -> category_id.equals(event.getCategory_id()))
                .collect(Collectors.toList());

        if (filteredEvents.isEmpty()) {
            // Return 404 with a custom message if no events are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Events found for category ID: " + category_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredEvents);
        }
    }


    //------------------------------------Trip Plan----------------------------------

//    // Create a new plan from user
//    @PostMapping("/plan/create")
//    public ResponseEntity<?> createPlanDetails(@RequestBody Plan plan) {
//        System.out.println("Received Plan: " + plan); // Log the incoming plan
//        // Save the destination
//        planService.createPlan(plan);
//
//        // Response message
//        String message = "Plan with id: " + plan.getPlan_id() + " Created Succesfully";
//        return ResponseEntity.status(HttpStatus.CREATED).body(message);
//    }
//
//
//    // Get details of a specific destination by ID
//    @GetMapping("/plan/{plan_id}")
//    public ResponseEntity<Plan> getPlanDetails(@PathVariable("plan_id") String plan_id) {
//        Plan planDetails = planService.getPlan(plan_id);
//        if (planDetails != null) {
//            return ResponseEntity.ok(planDetails); // Return 200 OK with the destination
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the destination doesn't exist
//        }
//    }

}
