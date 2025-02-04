package com.OlympusRiviera.controller.Destination;

import com.OlympusRiviera.model.Destination.DestinationStat;
import com.OlympusRiviera.service.Destination.DestinationStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DestinationStatController {

    private final DestinationStatService destinationStatService;

    public DestinationStatController(DestinationStatService destinationStatService){
        this.destinationStatService = destinationStatService;
    }





    //checks if there is a statistic for a destination and if exist then not allowing to create
    //another one

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/destination/statistics/create")
    public ResponseEntity<String> createDestinationStat(@RequestBody DestinationStat destinationStat) {
        // Check if a record with the same destination_id already exists
        List<DestinationStat> existingStats = destinationStatService.getAllDestinationStats()
                .stream()
                .filter(stat -> stat.getDestination_id().equals(destinationStat.getDestination_id()))
                .collect(Collectors.toList());

        if (!existingStats.isEmpty()) {
            // Return 409 Conflict if a record already exists for the given destination_id
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A statistic already exists for destination ID: " + destinationStat.getDestination_id());
        }

        // Save the new record
        destinationStatService.createDestinationStat(destinationStat);
        String message = "Statistic with id: " + destinationStat.getStat_id() + " Created Successfully for destination with id: " + destinationStat.getDestination_id();
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }


    //update stats for specified destination

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/destination/statistics/{destination_id}")
    public ResponseEntity<String> updateDestinationStat(
            @PathVariable("destination_id") String destination_id,
            @RequestBody DestinationStat updatedStat) {

        // Fetch all stats
        List<DestinationStat> allStats = destinationStatService.getAllDestinationStats();

        // Filter stats by destination_id
        List<DestinationStat> matchingStats = allStats.stream()
                .filter(stat -> destination_id.equals(stat.getDestination_id()))
                .collect(Collectors.toList());

        if (matchingStats.isEmpty()) {
            // Return 404 if no stats are found for the given destination_id
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for destination ID: " + destination_id + ". Unable to update.");
        } else {
            // Update all matching stats
            for (DestinationStat stat : matchingStats) {
                stat.setTotal_visits(updatedStat.getTotal_visits());
                stat.setAverage_rating(updatedStat.getAverage_rating());
                stat.setTotal_wishlist_items(updatedStat.getTotal_wishlist_items());
                stat.setTotal_feedback_given(updatedStat.getTotal_feedback_given());
                stat.setUpdatedAt(new java.util.Date()); // Update timestamp

                // Save the updated stat
                destinationStatService.updateDestinationStat(stat);
            }

            // Return success message
            return ResponseEntity.ok("Statistics for destination ID: " + destination_id + " updated successfully.");
        }
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/destination/statistics/{destination_id}")
    public ResponseEntity<String> deleteDestinationStat(@PathVariable String destination_id) {
        // Fetch all stats
        List<DestinationStat> allStats = destinationStatService.getAllDestinationStats();

        // Check if any stats match the destination_id
        boolean exists = allStats.stream()
                .anyMatch(stat -> destination_id.equals(stat.getDestination_id()));

        if (!exists) {
            // Return 404 with a custom message if no stats are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for destination ID: " + destination_id);
        } else {
            // Delete all stats for the given destination_id
            allStats.stream()
                    .filter(stat -> destination_id.equals(stat.getDestination_id()))
                    .forEach(stat -> destinationStatService.deleteDestinationStat(stat.getStat_id()));

            // Return 200 OK with a success message
            String message = "All statistics for destination ID: " + destination_id + " deleted successfully.";
            return ResponseEntity.ok(message);
        }
    }








}
