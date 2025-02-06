package com.DiscoverHellas.service.Activity;

import com.DiscoverHellas.model.Activity.ActivityStat;

import java.util.List;

public interface ActivityStatService {

    public String createActivityStat(ActivityStat activityStat);
    public String updateActivityStat(ActivityStat activityStat);
    public String deleteActivityStat(String stat_id);
    public ActivityStat getActivityStat(String stat_id);
    public List<ActivityStat> getAllActivityStats();
}
