package com.OlympusRiviera.controller.Activity;

import com.OlympusRiviera.model.Activity.ActivityCategory;
import com.OlympusRiviera.service.Activity.ActivityCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ActivityCategoryController {

    private final ActivityCategoryService activityCategoryService;

    public ActivityCategoryController(ActivityCategoryService activityCategoryService) {
        this.activityCategoryService = activityCategoryService;
    }

    // Get details of a specific activity category by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/activity/category/{category_id}")
    public ResponseEntity<ActivityCategory> getActivityCategoryDetails(@PathVariable("category_id") String category_id) {
        ActivityCategory activityCategoryDetails = activityCategoryService.getActivityCategory(category_id);
        if (activityCategoryDetails != null) {
            return ResponseEntity.ok(activityCategoryDetails); // Return 200 OK with the activity category
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if the activity category doesn't exist
        }
    }


    // Create a new activity category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/activity/category/create")
    public ResponseEntity<String> createActivityCategoryDetails(@RequestBody ActivityCategory activityCategory) {
        activityCategoryService.createActivityCategory(activityCategory);
        String message = "Activity Category with id: " + activityCategory.getCategory_id() + " Created Successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(message); // Return 201 Created
    }

    // Update an existing activity category by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/activity/category/{category_id}")
    public ResponseEntity<String> updateActivityCategory(@PathVariable String category_id, @RequestBody ActivityCategory activityCategory) {
        activityCategory.setCategory_id(category_id);
        activityCategoryService.updateActivityCategory(activityCategory);
        String message = "Activity Category with id: " + activityCategory.getCategory_id() + " Updated Successfully";
        return ResponseEntity.ok(message); // Return 200 OK
    }

    // Delete an activity category by ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/activity/category/{category_id}")
    public ResponseEntity<String> deleteActivityCategory(@PathVariable String category_id) {
        activityCategoryService.deleteActivityCategory(category_id);
        String message = "Activity Category with id: " + category_id + " Deleted Successfully";
        return ResponseEntity.status(HttpStatus.OK).body(message); // Return 204 No Content after deletion
    }
}
