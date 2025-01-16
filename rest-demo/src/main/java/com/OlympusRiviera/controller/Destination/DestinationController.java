package com.OlympusRiviera.controller.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.service.Destination.DestinationService;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://olumpus-riviera-frontend.vercel.app")
@RestController
@RequestMapping("/api")
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationStatService destinationStatService;

    public DestinationController(DestinationService destinationService, DestinationStatService destinationStatService) {
        this.destinationService = destinationService;
        this.destinationStatService = destinationStatService;
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

    // Get all destination details
    @GetMapping("/destination/get/all")
    public ResponseEntity<List<Destination>> getAllDestinationDetails() {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations); // Return 200 OK with the list of destinations
    }

    // Create a new destination from POTAP

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/destination/create")
    public ResponseEntity<String> createDestinationDetails(@RequestBody Destination destination) {
        // Save the destination
        destinationService.createDestination(destination);

        // Initialize the DestinationStat object
        DestinationStat destinationStat = new DestinationStat();
        destinationStat.setDestination_id(destination.getDestination_id());
        destinationStat.setTotal_visits(0);
        destinationStat.setAverage_rating(0.0f);
        destinationStat.setTotal_wishlist_items(0);
        destinationStat.setTotal_feedback_given(0);

        // Save the DestinationStat
        destinationStatService.createDestinationStat(destinationStat);

        // Response message
        String message = "Destination with id: " + destination.getDestination_id() + " and associated statistics created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    // Update an existing destination by ID

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/destination/{destination_id}")
    public ResponseEntity<String> updateDestination(@PathVariable String destination_id, @RequestBody Destination destination) {
        destination.setDestination_id(destination_id);
        destinationService.updateDestination(destination);
        String message = "Destination with id: " + destination.getDestination_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    // Delete a destination by ID

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/destination/{destination_id}")
    public ResponseEntity<String> deleteDestination(@PathVariable String destination_id) {
        destinationService.deleteDestination(destination_id);
        String message = "Destination with id: " + destination_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    //get all destinations from specific category id
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
}
