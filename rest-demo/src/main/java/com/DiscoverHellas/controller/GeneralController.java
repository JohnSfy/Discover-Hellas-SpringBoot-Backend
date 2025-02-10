package com.DiscoverHellas.controller;

import com.DiscoverHellas.model.Activity.Activity;
import com.DiscoverHellas.model.Activity.ActivityCategory;
import com.DiscoverHellas.model.Activity.ActivityStat;
import com.DiscoverHellas.model.Amenity.Amenity;
import com.DiscoverHellas.model.Amenity.AmenityCategory;
import com.DiscoverHellas.model.Approval.Approval;
import com.DiscoverHellas.model.Destination.Destination;
import com.DiscoverHellas.model.Destination.DestinationCategory;
import com.DiscoverHellas.model.Destination.DestinationStat;
import com.DiscoverHellas.model.Event.Event;
import com.DiscoverHellas.model.Plan.Plan;
import com.DiscoverHellas.model.Review.Review;
import com.DiscoverHellas.service.Activity.ActivityCategoryService;
import com.DiscoverHellas.service.Activity.ActivityService;
import com.DiscoverHellas.service.Activity.ActivityStatService;
import com.DiscoverHellas.service.Amenity.AmenityCategoryService;
import com.DiscoverHellas.service.Amenity.AmenityService;
import com.DiscoverHellas.service.Approval.ApprovalService;
import com.DiscoverHellas.service.Destination.DestinationCategoryService;
import com.DiscoverHellas.service.Destination.DestinationService;
import com.DiscoverHellas.service.Destination.DestinationStatService;
import com.DiscoverHellas.service.Event.EventService;
import com.DiscoverHellas.service.Plan.PlanService;
import com.DiscoverHellas.service.Review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    private final EventService eventService;
    private final PlanService planService;
    private final ReviewService reviewService;
    private final ApprovalService approvalService;
    private final DestinationStatService destinationStatService;
    private final DestinationService destinationService;
    private final DestinationCategoryService destinationCategoryService;
    private final ActivityStatService activityStatService;
    private final ActivityService activityService;
    private final ActivityCategoryService activityCategoryService;

    public GeneralController(AmenityService amenityService, AmenityCategoryService amenityCategoryService, EventService eventService, PlanService planService, ReviewService reviewService, ApprovalService approvalService, DestinationStatService destinationStatService, DestinationService destinationService, DestinationCategoryService destinationCategoryService, ActivityStatService activityStatService, ActivityService activityService, ActivityCategoryService activityCategoryService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
        this.eventService = eventService;
        this.planService = planService;
        this.reviewService = reviewService;
        this.approvalService = approvalService;
        this.destinationStatService = destinationStatService;
        this.destinationService = destinationService;
        this.destinationCategoryService = destinationCategoryService;
        this.activityStatService = activityStatService;
        this.activityService = activityService;
        this.activityCategoryService = activityCategoryService;
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

    // Update a plan from user(you can add entities in the plan by sending them in the body, ithout sending all the previous body)
    @PutMapping("/plan/{plan_id}")
    public ResponseEntity<?> updatePlanDetails(
            @PathVariable("plan_id") String plan_id,
            @RequestBody Map<String, Object> updates) {

        // Fetch the existing plan from the service layer
        Plan existingPlan = planService.getPlan(plan_id);

        if (existingPlan != null) {
            // Update the title if provided
            if (updates.containsKey("title")) {
                String updatedTitle = (String) updates.get("title");
                if (updatedTitle != null) {
                    existingPlan.setTitle(updatedTitle);
                }
            }

            // Update the user_id if provided
            if (updates.containsKey("user_id")) {
                String updatedUserId = (String) updates.get("user_id");
                if (updatedUserId != null) {
                    existingPlan.setUser_id(updatedUserId);
                }
            }

            // Handle the plan field if provided
            if (updates.containsKey("plan")) {
                Object planUpdates = updates.get("plan");

                try {
                    ObjectMapper objectMapper = new ObjectMapper();

                    // Parse the existing plan into a list
                    List<Map<String, Object>> existingPlanList = existingPlan.getPlanAsObject(List.class);

                    // Check if the provided plan updates are a list or a single object
                    if (planUpdates instanceof List) {
                        List<Map<String, Object>> newPlanEntries = objectMapper.convertValue(planUpdates, List.class);

                        for (Map<String, Object> newPlanEntry : newPlanEntries) {
                            // Check if the entry already exists in the plan
                            boolean exists = existingPlanList.stream().anyMatch(existingEntry ->
                                    existingEntry.get("entity_id").equals(newPlanEntry.get("entity_id")));

                            if (!exists) {
                                existingPlanList.add(newPlanEntry);
                            }
                        }
                    } else {
                        Map<String, Object> newPlanEntry = objectMapper.convertValue(planUpdates, Map.class);

                        // Check if the entry already exists in the plan
                        boolean exists = existingPlanList.stream().anyMatch(existingEntry ->
                                existingEntry.get("entity_id").equals(newPlanEntry.get("entity_id")));

                        if (!exists) {
                            existingPlanList.add(newPlanEntry);
                        }
                    }

                    // Update the plan field with the merged list
                    existingPlan.setPlan(existingPlanList);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid JSON format for plan field.");
                }
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

    //you can delete destination from the plan by sending entities ids
    @PutMapping("/plan/{plan_id}/remove")
    public ResponseEntity<?> removePlanEntities(
            @PathVariable("plan_id") String plan_id,
            @RequestBody Map<String, Object> updates) {

        // Fetch the existing plan from the service layer
        Plan existingPlan = planService.getPlan(plan_id);

        if (existingPlan != null) {
            // Get the list of entity_ids to be removed from the request
            if (updates.containsKey("entity_ids")) {
                List<String> entityIdsToRemove = (List<String>) updates.get("entity_ids");

                // Get the existing plan data as a list
                List<Map<String, Object>> existingPlanList = existingPlan.getPlanAsObject(List.class);

                // Filter out the entries that match the entity_ids to be removed
                List<Map<String, Object>> updatedPlanList = existingPlanList.stream()
                        .filter(entry -> !entityIdsToRemove.contains(entry.get("entity_id")))
                        .collect(Collectors.toList());

                // Update the plan field with the updated list
                existingPlan.setPlan(updatedPlanList);

                // Update the 'updatedAt' field to the current time
                existingPlan.setUpdatedAt(new Date());

                // Save the updated plan to the database
                planService.updatePlan(existingPlan);

                // Response message
                String message = "Plan with id: " + existingPlan.getPlan_id() + " updated successfully by removing specified entities.";

                return ResponseEntity.status(HttpStatus.OK).body(message);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No entity_ids provided for removal.");
            }
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



    // Get all plans for a specific user by user_id
    @GetMapping("/plan/user/{user_id}/plans")
    public ResponseEntity<?> getPlansByUser(@PathVariable("user_id") String user_id,
                                            @RequestParam(name = "planType", defaultValue = "list") String planType) {
        // Fetch all plans
        List<Plan> allPlans = planService.getAllPlans();

        // Filter plans by user_id
        List<Plan> filteredPlans = allPlans.stream()
                .filter(plan -> user_id.equals(plan.getUser_id()))
                .collect(Collectors.toList());

        if (filteredPlans.isEmpty()) {
            // Return 404 with a custom message if no plans are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No plans found for user ID: " + user_id);
        } else {
            // Map filtered plans to a detailed response structure
            List<Map<String, Object>> responseList = filteredPlans.stream().map(plan -> {
                Map<String, Object> response = new HashMap<>();
                Object parsedPlan;

                // Check the type requested and parse the 'plan' field accordingly
                if ("list".equalsIgnoreCase(planType)) {
                    // If the planType is "list", parse it as a List
                    parsedPlan = plan.getPlanAsObject(List.class);
                } else if ("map".equalsIgnoreCase(planType)) {
                    // If the planType is "map", parse it as a Map
                    parsedPlan = plan.getPlanAsObject(Map.class);
                } else {
                    // Default case: Return the raw 'plan' string or handle differently
                    parsedPlan = plan.getPlan();
                }

                response.put("plan_id", plan.getPlan_id());
                response.put("title", plan.getTitle());
                response.put("plan", parsedPlan);
                response.put("user_id", plan.getUser_id());
                response.put("createdAt", plan.getCreatedAt());
                response.put("updatedAt", plan.getUpdatedAt());
                return response;
            }).collect(Collectors.toList());

            // Return 200 OK with the detailed list
            return ResponseEntity.ok(responseList);
        }
    }



    //----------------------------Review Destination / Activity------------------------

    @PostMapping("/feedback/evaluation/create")
    public ResponseEntity<?> createEntityReview(@RequestBody Review review) {
        review.setStatus("PENDING");
        reviewService.createReview(review);
        // Create a new Approval record
        Approval approval = new Approval();
        approval.setEntity_id(review.getReview_id());
        approval.setEntity_type("Review");
        approval.setApproval_type("Create");
        approval.setStatus("PENDING");
        approval.setUser_id(review.getUser_id());

        approvalService.createApproval(approval);

        // Create a success message
        String message = "Review with id: " + review.getReview_id() + " created successfully and pending approval " +"with id "+ approval.getApproval_id();
        // Response message
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    // Get all reviews for a specific destination or activity
    @GetMapping("/feedback/get/{entity_id}/evaluation/get/all")
    public ResponseEntity<?> getAllReviewsForEntity(@PathVariable String entity_id) {
        try {
            // Fetch all reviews
            List<Review> allReviews = reviewService.getAllReviews();

            // Filter reviews by entity_id
            List<Review> filteredReviews = allReviews.stream()
                    .filter(review -> entity_id.equals(review.getEntity_id()) &&
                            "APPROVED".equals(review.getStatus()) &&
                            "VISIBLE".equals(review.getView())
                             )
                    .collect(Collectors.toList());

            // Check if any reviews match the entity_id
            if (filteredReviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No reviews found for entity_id: " + entity_id);
            }

            // Return the filtered reviews
            return ResponseEntity.ok(filteredReviews); // 200 OK with the list of reviews
        } catch (Exception e) {
            // Handle unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching reviews: " + e.getMessage());
        }
    }




//------------------------------Destinations------------------------------

    // Get all stats for destinations
    @GetMapping("/destination/statistics/get/all")
    public ResponseEntity<List<DestinationStat>> getAllDestinationStatisticDetails() {
        List<DestinationStat> destinationStats = destinationStatService.getAllDestinationStats();
        return ResponseEntity.ok(destinationStats); // Return 200 OK with the list of destinations
    }

    // Get all destination details
    @GetMapping("/destination/get/all")
    public ResponseEntity<List<Destination>> getAllDestinationDetails() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations); // Return 200 OK with the list of destinations
    }

    // Get details of a specific destination by ID
    @GetMapping("/destination/{destination_id}")
    public ResponseEntity<Destination> getDestinationDetails(@PathVariable("destination_id") String destination_id) {
        Destination destinationDetails = destinationService.getDestination(destination_id);
        if (destinationDetails != null) {
            return ResponseEntity.ok(destinationDetails); // Return 200 OK with the destination
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the destination doesn't exist
        }
    }

    // Get all destinations from a specific category id
    @GetMapping("/destination/{category_id}/destinations")
    public ResponseEntity<?> getDestinationsByCategory(@PathVariable("category_id") String category_id) {
        // Fetch all destinations
        List<Destination> allDestinations = destinationService.getAllDestinations();

        // Filter destinations by category_id
        List<Destination> filteredDestinations = allDestinations.stream()
                .filter(destination -> category_id.equals(destination.getCategory_id()))
                .collect(Collectors.toList());

        if (filteredDestinations.isEmpty()) {
            // Return 404 with a custom message if no destinations are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No destinations found for category ID: " + category_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredDestinations);
        }
    }


    //getStats for the specified destination, if the dest stat is null, informs

    @GetMapping("/destination/statistics/{destination_id}")
    public ResponseEntity<?> getStatsByDestinationId(@PathVariable("destination_id") String destination_id) {
        // Fetch all stats
        List<DestinationStat> allStats = destinationStatService.getAllDestinationStats();

        // Filter stats by destination_id
        List<DestinationStat> filteredStats = allStats.stream()
                .filter(stat -> destination_id.equals(stat.getDestination_id()))
                .collect(Collectors.toList());

        if (filteredStats.isEmpty()) {
            // Return 404 with a custom message if no stats are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for destination ID: " + destination_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredStats);
        }
    }

    // Get all destination Categories
    @GetMapping("/destination/category/get/all")
    public ResponseEntity<List<DestinationCategory>> getAllDestinationCategoryDetails() {
        List<DestinationCategory> destinationCategories = destinationCategoryService.getAllDestinationCategories();
        return ResponseEntity.ok(destinationCategories); // Return 200 OK with the list of destination categories
    }


//--------------------------Activities---------------------------------------

    // Get all stats for activities
    @GetMapping("/activity/statistics/get/all")
    public ResponseEntity<List<ActivityStat>> getAllActivityStats() {
        List<ActivityStat> activityStats = activityStatService.getAllActivityStats();
        return ResponseEntity.ok(activityStats); // Return 200 OK with the list of activity stats
    }

    // Get all activity details
    @GetMapping("/activity/get/all")
    public ResponseEntity<List<Activity>> getAllActivityDetails() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities); // Return 200 OK with the list of activities
    }

    // Get details of a specific activity by ID
    @GetMapping("/activity/{activity_id}")
    public ResponseEntity<Activity> getActivityDetails(@PathVariable("activity_id") String activity_id) {
        Activity activityDetails = activityService.getActivity(activity_id);
        if (activityDetails != null) {
            return ResponseEntity.ok(activityDetails); // Return 200 OK with the activity
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the activity doesn't exist
        }
    }

    // Get all activities from a specific category id
    @GetMapping("/activity/{category_id}/activities")
    public ResponseEntity<?> getActivitiesByCategory(@PathVariable("category_id") String category_id) {
        // Fetch all activities
        List<Activity> allActivities = activityService.getAllActivities();

        // Filter activities by category_id
        List<Activity> filteredActivities = allActivities.stream()
                .filter(activity -> category_id.equals(activity.getCategory_id()))
                .collect(Collectors.toList());

        if (filteredActivities.isEmpty()) {
            // Return 404 with a custom message if no activities are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No activities found for category ID: " + category_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredActivities);
        }
    }

    // Get stats for the specified activity, if the stat is null, informs
    @GetMapping("/activity/statistics/{activity_id}")
    public ResponseEntity<?> getStatsByActivityId(@PathVariable("activity_id") String activity_id) {
        // Fetch all stats
        List<ActivityStat> allStats = activityStatService.getAllActivityStats();

        // Filter stats by activity_id
        List<ActivityStat> filteredStats = allStats.stream()
                .filter(stat -> activity_id.equals(stat.getActivity_id()))
                .collect(Collectors.toList());

        if (filteredStats.isEmpty()) {
            // Return 404 with a custom message if no stats are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for activity ID: " + activity_id);
        } else {
            // Return 200 OK with the filtered list
            return ResponseEntity.ok(filteredStats);
        }
    }

    // Get all activity category details
    @GetMapping("/activity/category/get/all")
    public ResponseEntity<List<ActivityCategory>> getAllActivityCategoryDetails() {
        List<ActivityCategory> activityCategories = activityCategoryService.getAllActivityCategories();
        return ResponseEntity.ok(activityCategories); // Return 200 OK with the list of activity categories
    }


}
