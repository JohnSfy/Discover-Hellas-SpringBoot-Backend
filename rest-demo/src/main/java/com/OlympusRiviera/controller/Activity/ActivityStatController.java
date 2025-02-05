package com.OlympusRiviera.controller.Activity;

import com.OlympusRiviera.model.Activity.ActivityStat;
import com.OlympusRiviera.service.Activity.ActivityStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ActivityStatController {

    private final ActivityStatService activityStatService;

    public ActivityStatController(ActivityStatService activityStatService) {
        this.activityStatService = activityStatService;
    }


    //NOT USED
    // Checks if there is a statistic for an activity and if it exists then not allowing to create
    // another one
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/activity/statistics/create")
    public ResponseEntity<String> createActivityStat(@RequestBody ActivityStat activityStat) {
        // Check if a record with the same activity_id already exists
        List<ActivityStat> existingStats = activityStatService.getAllActivityStats()
                .stream()
                .filter(stat -> stat.getActivity_id().equals(activityStat.getActivity_id()))
                .collect(Collectors.toList());

        if (!existingStats.isEmpty()) {
            // Return 409 Conflict if a record already exists for the given activity_id
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A statistic already exists for activity ID: " + activityStat.getActivity_id());
        }

        // Save the new record
        activityStatService.createActivityStat(activityStat);
        String message = "Statistic with id: " + activityStat.getStat_id() + " Created Successfully for activity with id: " + activityStat.getActivity_id();
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    //NOT USED
    // Update stats for specified activity
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/activity/statistics/{activity_id}")
    public ResponseEntity<String> updateActivityStat(
            @PathVariable("activity_id") String activity_id,
            @RequestBody ActivityStat updatedStat) {

        // Fetch all stats
        List<ActivityStat> allStats = activityStatService.getAllActivityStats();

        // Filter stats by activity_id
        List<ActivityStat> matchingStats = allStats.stream()
                .filter(stat -> activity_id.equals(stat.getActivity_id()))
                .collect(Collectors.toList());

        if (matchingStats.isEmpty()) {
            // Return 404 if no stats are found for the given activity_id
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for activity ID: " + activity_id + ". Unable to update.");
        } else {
            // Update all matching stats
            for (ActivityStat stat : matchingStats) {
                stat.setTotal_visits(updatedStat.getTotal_visits());
                stat.setAverage_rating(updatedStat.getAverage_rating());
                stat.setTotal_wishlist_items(updatedStat.getTotal_wishlist_items());
                stat.setTotal_feedback_given(updatedStat.getTotal_feedback_given());
                stat.setUpdatedAt(new java.util.Date()); // Update timestamp

                // Save the updated stat
                activityStatService.updateActivityStat(stat);
            }

            // Return success message
            return ResponseEntity.ok("Statistics for activity ID: " + activity_id + " updated successfully.");
        }
    }

    //NOT USED
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/activity/statistics/{activity_id}")
    public ResponseEntity<String> deleteActivityStat(@PathVariable String activity_id) {
        // Fetch all stats
        List<ActivityStat> allStats = activityStatService.getAllActivityStats();

        // Check if any stats match the activity_id
        boolean exists = allStats.stream()
                .anyMatch(stat -> activity_id.equals(stat.getActivity_id()));

        if (!exists) {
            // Return 404 with a custom message if no stats are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No statistics found for activity ID: " + activity_id);
        } else {
            // Delete all stats for the given activity_id
            allStats.stream()
                    .filter(stat -> activity_id.equals(stat.getActivity_id()))
                    .forEach(stat -> activityStatService.deleteActivityStat(stat.getStat_id()));

            // Return 200 OK with a success message
            String message = "All statistics for activity ID: " + activity_id + " deleted successfully.";
            return ResponseEntity.ok(message);
        }
    }
}
