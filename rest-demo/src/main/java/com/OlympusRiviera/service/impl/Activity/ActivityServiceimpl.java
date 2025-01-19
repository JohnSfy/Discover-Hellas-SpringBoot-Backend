package com.OlympusRiviera.service.impl.Activity;

import com.OlympusRiviera.model.Activity.Activity;
import com.OlympusRiviera.repository.Activity.ActivityRepository;
import com.OlympusRiviera.service.Activity.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceimpl implements ActivityService {

    ActivityRepository activityRepository;

    public ActivityServiceimpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Implement the methods for interacting with the activity repository
    @Override
    public String createActivity(Activity activity) {
        activityRepository.save(activity);
        return "Success";
    }

    @Override
    public String updateActivity(Activity activity) {
//        more logic
        activityRepository.save(activity);
        return "Update Success";
    }

    @Override
    public String deleteActivity(String activity_id) {
        activityRepository.deleteById(activity_id);
        return "Deleted Success";
    }

    @Override
    public Activity getActivity(String activity_id) {
        return activityRepository.findById(activity_id).get();
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

}
