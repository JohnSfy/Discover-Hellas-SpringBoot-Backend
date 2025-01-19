package com.OlympusRiviera.service.Activity;

import com.OlympusRiviera.model.Activity.Activity;

import java.util.List;

public interface ActivityService {

    public String createActivity(Activity activity);
    public String updateActivity(Activity activity);
    public String deleteActivity(String activity_id);
    public Activity getActivity(String activity_id);
    public List<Activity> getAllActivities();
}
