package com.DiscoverHellas.controller.Amenity_Event;


import com.DiscoverHellas.model.Amenity.Amenity;
import com.DiscoverHellas.model.Amenity.AmenityCategory;
import com.DiscoverHellas.model.Event.Event;
import com.DiscoverHellas.model.Review.Review;
import com.DiscoverHellas.model.User.ProviderUser;
import com.DiscoverHellas.model.User.Role;
import com.DiscoverHellas.model.User.User;
import com.DiscoverHellas.service.Amenity.AmenityCategoryService;
import com.DiscoverHellas.service.Amenity.AmenityService;
import com.DiscoverHellas.service.Event.EventCategoryService;
import com.DiscoverHellas.service.Event.EventService;
import com.DiscoverHellas.service.Review.ReviewService;
import com.DiscoverHellas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AmenityService amenityService;
    private final AmenityCategoryService amenityCategoryService;
    private final EventService eventService;
    private final EventCategoryService eventCategoryService;
    private final ReviewService reviewService;
    private final UserService userService;


    public AdminController(AmenityService amenityService, AmenityCategoryService amenityCategoryService, EventService eventService, EventCategoryService eventCategoryService, ReviewService reviewService, UserService userService) {
        this.amenityService = amenityService;
        this.amenityCategoryService = amenityCategoryService;
        this.eventService = eventService;
        this.eventCategoryService = eventCategoryService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    //----------------------------Amenity----------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/amenity/get/all")
    public ResponseEntity<List<Amenity>> getAllAmenityDetails() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities); // Return 200 OK with the list of destinations
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")// Create a new amenity from potap with status approved
    @PostMapping("/amenity/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody Amenity amenity) {
        amenity.setStatus("APPROVED");
        amenityService.createAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " Created Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/amenity/edit/{amenity_id}")
    public ResponseEntity<String> updateAmenity(@PathVariable String amenity_id, @RequestBody Amenity amenity) {
        amenity.setAmenity_id(amenity_id);
        amenity.setStatus("APPROVED"); //Because ΠΟΤΑΠ UPDATED the amenity
        amenityService.updateAmenity(amenity);
        String message = "Amenity with id: " + amenity.getAmenity_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/amenity/delete/{amenity_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String amenity_id) {
        amenityService.deleteAmenity(amenity_id);
        String message = "Amenity with id: " + amenity_id + " Deleted Successfully from ΠΟΤΑΠ";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Use 200 OK instead
    }


    //-----------------------Amenity Category Control-----------------------------------


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/amenity/category/create")
    public ResponseEntity<String> createAmenityDetails(@RequestBody AmenityCategory amenityCategory) {
        amenityCategoryService.createAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/amenity/category/{category_id}")
    public ResponseEntity<String> updateAmenityCategory(@PathVariable String category_id, @RequestBody AmenityCategory amenityCategory) {
        amenityCategory.setCategory_id(category_id);
        amenityCategoryService.updateAmenityCategory(amenityCategory);
        String message = "Amenity Category with id: " + amenityCategory.getCategory_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/amenity/category/{category_id}")
    public ResponseEntity<String> deleteAmenityCategory(@PathVariable String category_id) {
        amenityCategoryService.deleteAmenityCategory(category_id);
        String message = "Amenity Category with id: " + category_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
    }


    //-----------------------------------Event------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/event/get/all")
    public ResponseEntity<List<Event>> getAllEventDetails() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events); // Return 200 OK with the list of destinations
    }

//    // Create a new event from potap with status approved
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/event/create")
//    public ResponseEntity<String> createEventDetails(@RequestBody Event event) {
//        event.setStatus("APPROVED");
//        eventService.createEvent(event);
//        String message = "Event with id: " + event.getEvent_id() + " Created Successfully from ΠΟΤΑΠ";
//        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/event/edit/{event_id}")
//    public ResponseEntity<String> updateEvent(@PathVariable String event_id, @RequestBody Event event) {
//        event.setEvent_id(event_id);
//        event.setStatus("APPROVED"); //Because ΠΟΤΑΠ UPDATED the amenity
//        eventService.updateEvent(event);
//        String message = "Amenity with id: " + event.getEvent_id() + " Updated Successfully";
//        return ResponseEntity.ok(message); // Return 200 OK
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/event/delete/{event_id}")
//    public ResponseEntity<String> deleteEvent(@PathVariable String event_id) {
//        eventService.deleteEvent(event_id);
//        String message = "Event with id: " + event_id + " Deleted Successfully from ΠΟΤΑΠ";
//        return ResponseEntity.status(HttpStatus.OK).body(message); // Use 200 OK instead
//    }

//    //-----------------------Event Category Control-----------------------------------
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/event/category/create")
//    public ResponseEntity<String> createEventDetails(@RequestBody EventCategory eventCategory) {
//        eventCategoryService.createEventCategory(eventCategory);
//        String message = "Event Category with id: " + eventCategory.getCategory_id() + " Created Successfully";
//        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/event/category/{category_id}")
//    public ResponseEntity<String> updateEventCategory(@PathVariable String category_id, @RequestBody EventCategory eventCategory) {
//        eventCategory.setCategory_id(category_id);
//        eventCategoryService.updateEventCategory(eventCategory);
//        String message = "Event Category with id: " + eventCategory.getCategory_id() + " Updated Successfully";
//        return ResponseEntity.ok(message); // Return 200 OK
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/event/category/{category_id}")
//    public ResponseEntity<String> deleteEventCategory(@PathVariable String category_id) {
//        eventCategoryService.deleteEventCategory(category_id);
//        String message = "Event Category with id: " + category_id + " Deleted Successfully";
//        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
//    }


    //---------------------------------Reviews------------------------------------------


    // Get all reviews
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/feedback/evaluation/get/all")
    public ResponseEntity<?> getAllReviews() {

        try {
            // Fetch all reviews
            List<Review> allReviews = reviewService.getAllReviews();

            // Return the filtered reviews
            return ResponseEntity.ok(allReviews); // 200 OK with the list of reviews
        } catch (Exception e) {
            // Handle unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching reviews: " + e.getMessage());
        }
    }


    // Get a specific  reviews
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/feedback/evaluation/get/{review_id}")
    public ResponseEntity<?> getSpecificReview(@PathVariable String review_id) {

        try {
            // Fetch all reviews
            Review review = reviewService.getReview(review_id);

            // Return the filtered reviews
            return ResponseEntity.ok(review); // 200 OK with the list of reviews
        } catch (Exception e) {
            // Handle unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching reviews: " + e.getMessage());
        }
    }


    //Update the view visible/hidden with ?visible=true/False
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/feedback/evaluation/get/{review_id}/view")
    public ResponseEntity<?> updateReviewVisibility(@PathVariable String review_id,
                                                    @RequestParam("visible") String visible) {
        // Fetch the Review object by its ID
        Review review = reviewService.getReview(review_id);
        if (review == null) {
            // Return 404 if the review is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review not found for ID: " + review_id);
        }

        // Validate and parse the 'visible' string
        if (!"true".equalsIgnoreCase(visible) && !"false".equalsIgnoreCase(visible)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid value for 'visible'. Expected 'true' or 'false'.");
        }

        // Convert string to boolean
        boolean isVisible = Boolean.parseBoolean(visible);

        // Update the visibility status
        review.setView(isVisible ? "VISIBLE" : "HIDDEN");
        reviewService.updateReview(review);

        // Return success message
        String visibilityStatus = isVisible ? "now visible to the public" : "no longer visible to the public";
        return ResponseEntity.ok("Review ID: " + review_id + " is " + visibilityStatus + ".");
    }


    //-----------------------------Users-----------------------------------

    //Get All Providers (PENDING OR NOT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/providers/get/all")
    public ResponseEntity<?> getAllProviders() {
        try {
            // Fetch all users
            List<User> allUsers = userService.getAllUsers();

            // Filter users with role "PROVIDER"
            List<User> providers = allUsers.stream()
                    .filter(user -> user.getRole() == Role.PROVIDER)
                    .collect(Collectors.toList());

            // Return the filtered providers
            return ResponseEntity.ok(providers);
        } catch (Exception e) {
            // Handle unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching providers: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/providers/get/{provider_id}/updateStatus") // ?status=APPROVED/REJECTED
    public ResponseEntity<String> updateProviderStatus(
            @PathVariable("provider_id") String provider_id,
            @RequestParam("status") String status) {

        // Validate the status parameter
        if (!"APPROVED".equalsIgnoreCase(status) && !"REJECTED".equalsIgnoreCase(status)) {
            return ResponseEntity.badRequest()
                    .body("Invalid status value. Allowed values are APPROVED or REJECTED.");
        }

        // Fetch the user by ID
        Optional<ProviderUser> providerOpt = userService.getProviderUser(provider_id);

        providerOpt.get().setStatus(status);
        userService.updateUser(Optional.of(providerOpt.get()));

//        // Return success message
        return ResponseEntity.ok("Provider ID: " + provider_id + " is now " + status);
    }


}

