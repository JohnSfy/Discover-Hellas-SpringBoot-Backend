package com.OlympusRiviera.controller;


import com.OlympusRiviera.model.Amenity.Amenity;
import com.OlympusRiviera.model.Amenity.AmenityCategory;
import com.OlympusRiviera.model.Event.Event;
import com.OlympusRiviera.model.Plan.Plan;
import com.OlympusRiviera.service.Amenity.AmenityCategoryService;
import com.OlympusRiviera.service.Amenity.AmenityService;
import com.OlympusRiviera.service.Event.EventService;
import com.OlympusRiviera.service.Plan.PlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class GeneralController {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    private final EventService eventService;
    private final PlanService planService;

    public GeneralController(AmenityService amenityService, AmenityCategoryService amenityCategoryService, EventService eventService, PlanService planService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
        this.eventService = eventService;
        this.planService = planService;
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

    // Create a new plan from user
    @PostMapping("/plan/create")
    public ResponseEntity<?> createPlanDetails(@RequestBody Plan plan) {
        System.out.println("Received Plan: " + plan); // Log the incoming plan
        // Save the destination
        planService.createPlan(plan);

        // Response message
        String message = "Plan with id: " + plan.getPlan_id() + " Created Succesfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    // Get details of a specific destination by ID
    @GetMapping("/plan/{plan_id}")
    public ResponseEntity<?> getPlanDetails(
            @PathVariable("plan_id") String plan_id,
            @RequestParam(name = "planType", defaultValue = "list") String planType) {

        // Fetch the plan object
        Plan planDetails = planService.getPlan(plan_id);

        if (planDetails != null) {
            Object plan = null;

            // Check the type requested and parse the 'plan' field accordingly
            if ("list".equalsIgnoreCase(planType)) {
                // If the planType is "list", parse it as a List
                plan = planDetails.getPlanAsObject(List.class);
            } else if ("map".equalsIgnoreCase(planType)) {
                // If the planType is "map", parse it as a Map
                plan = planDetails.getPlanAsObject(Map.class);
            } else {
                // Default case: Return the raw 'plan' string or handle differently
                plan = planDetails.getPlan();
            }

            // Return the response with plan details and the parsed plan
            Map<String, Object> response = new HashMap<>();
            response.put("plan_id", planDetails.getPlan_id());
            response.put("title", planDetails.getTitle());
            response.put("plan", plan);
            response.put("user_id", planDetails.getUser_id());
            response.put("createdAt", planDetails.getCreatedAt());
            response.put("updatedAt", planDetails.getUpdatedAt());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found");
        }
    }

    // Update a plan from user
    @PutMapping("/plan/{plan_id}")
    public ResponseEntity<?> updatePlanDetails(
            @PathVariable("plan_id") String plan_id,
            @RequestBody Plan updatedPlan) {

        // Fetch the existing plan from the service layer
        Plan existingPlan = planService.getPlan(plan_id);

        if (existingPlan != null) {
            // Only update the fields that are provided in the request
            if (updatedPlan.getTitle() != null) {
                existingPlan.setTitle(updatedPlan.getTitle());
            }

            if (updatedPlan.getPlan() != null) {
                // Parse the incoming 'plan' field correctly if it's a valid JSON string
                String updatedPlanString = updatedPlan.getPlan();

                try {
                    // Ensure it's a valid JSON and handle it as needed
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object parsedPlan = objectMapper.readTree(updatedPlanString);  // Check if it's a valid JSON object/array
                    existingPlan.setPlan(parsedPlan);  // Re-serialize it into a string to store
                } catch (JsonProcessingException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid JSON format for plan field.");
                }
            }

            if (updatedPlan.getUser_id() != null) {
                existingPlan.setUser_id(updatedPlan.getUser_id());
            }

            // Update the 'updatedAt' field to the current time
            existingPlan.setUpdatedAt(new Date());

            // Save the updated plan to the database
            planService.updatePlan(existingPlan);

            // Response message
            String message = "Plan with id: " + existingPlan.getPlan_id() + " updated successfully";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            // Return 404 Not Found if the plan doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found");
        }
    }


    @DeleteMapping("/plan/{plan_id}")
    public ResponseEntity<?> deletePlanDetails(@PathVariable("plan_id") String plan_id) {
        // Fetch the existing plan from the service layer
        Plan existingPlan = planService.getPlan(plan_id);

        if (existingPlan != null) {
            // Delete the plan from the database
            planService.deletePlan(plan_id);

            // Response message indicating successful deletion
            String message = "Plan with id: " + plan_id + " has been deleted successfully.";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            // Return 404 Not Found if the plan doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan with id: " + plan_id + " not found.");
        }
    }







}
