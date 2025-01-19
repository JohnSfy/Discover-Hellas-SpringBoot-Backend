package com.OlympusRiviera.service.Activity;

import com.OlympusRiviera.model.Activity.ActivityCategory;

import java.util.List;

public interface ActivityCategoryService {

    public String createActivityCategory(ActivityCategory activityCategory);
    public String updateActivityCategory(ActivityCategory activityCategory);
    public String deleteActivityCategory(String category_id);
    public ActivityCategory getActivityCategory(String category_id);
    public List<ActivityCategory> getAllActivityCategories();
}
