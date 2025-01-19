package com.OlympusRiviera.controller.Activity;

import com.OlympusRiviera.model.Activity.Activity;
import com.OlympusRiviera.model.Activity.ActivityStat;
import com.OlympusRiviera.service.Activity.ActivityService;
import com.OlympusRiviera.service.Activity.ActivityStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityStatService activityStatService;

    public ActivityController(ActivityService activityService, ActivityStatService activityStatService) {
        this.activityService = activityService;
        this.activityStatService = activityStatService;
    }



    // Create a new activity from POTAP
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/activity/create")
    public ResponseEntity<String> createActivityDetails(@RequestBody Activity activity) {
        // Save the activity
        activityService.createActivity(activity);

        // Initialize the ActivityStat object
        ActivityStat activityStat = new ActivityStat();
        activityStat.setActivity_id(activity.getActivity_id());
        activityStat.setTotal_visits(0);
        activityStat.setAverage_rating(0.0f);
        activityStat.setTotal_wishlist_items(0);
        activityStat.setTotal_feedback_given(0);

        // Save the ActivityStat
        activityStatService.createActivityStat(activityStat);

        // Response message
        String message = "Activity with id: " + activity.getActivity_id() + " and associated statistics created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Update an existing activity by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/activity/{activity_id}")
    public ResponseEntity<String> updateActivity(@PathVariable String activity_id, @RequestBody Activity activity) {
        activity.setActivity_id(activity_id);
        activityService.updateActivity(activity);
        String message = "Activity with id: " + activity.getActivity_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    // Delete an activity by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/activity/{activity_id}")
    public ResponseEntity<String> deleteActivity(@PathVariable String activity_id) {
        activityService.deleteActivity(activity_id);
        String message = "Activity with id: " + activity_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


}
