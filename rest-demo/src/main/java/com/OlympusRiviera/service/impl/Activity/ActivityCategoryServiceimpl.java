package com.OlympusRiviera.service.impl.Activity;

import com.OlympusRiviera.model.Activity.ActivityCategory;
import com.OlympusRiviera.repository.Activity.ActivityCategoryRepository;
import com.OlympusRiviera.service.Activity.ActivityCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityCategoryServiceimpl implements ActivityCategoryService {
    ActivityCategoryRepository activityCategoryRepository;

    public ActivityCategoryServiceimpl(ActivityCategoryRepository activityCategoryRepository) {
        this.activityCategoryRepository = activityCategoryRepository;
    }

    public String createActivityCategory(ActivityCategory activityCategory) {
        activityCategoryRepository.save(activityCategory);
        return "Success";
    }

    @Override
    public String updateActivityCategory(ActivityCategory activityCategory) {
        // More logic
        activityCategoryRepository.save(activityCategory);
        return "Update Success";
    }

    public String deleteActivityCategory(String category_id) {
        activityCategoryRepository.deleteById(category_id);
        return "Deleted Success";
    }

    @Override
    public ActivityCategory getActivityCategory(String category_id) {
        return activityCategoryRepository.findById(category_id).get();
    }

    public List<ActivityCategory> getAllActivityCategories() {
        return activityCategoryRepository.findAll();
    }
}
