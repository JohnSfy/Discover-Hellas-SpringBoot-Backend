package com.OlympusRiviera.service.impl.Activity;

import com.OlympusRiviera.model.Activity.ActivityStat;
import com.OlympusRiviera.repository.Activity.ActivityStatRepository;
import com.OlympusRiviera.service.Activity.ActivityStatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityStatServiceimpl implements ActivityStatService {

    ActivityStatRepository activityStatRepository;

    public ActivityStatServiceimpl(ActivityStatRepository activityStatRepository){
        this.activityStatRepository = activityStatRepository;
    }

    // Implement the required methods here
    // Create, update, delete, and retrieve activity statistics
    public String createActivityStat(ActivityStat activityStat) {
        activityStatRepository.save(activityStat);
        return "Success";
    }

    @Override
    public String updateActivityStat(ActivityStat activityStat){
        activityStatRepository.save(activityStat);
        return "Update Success";
    }

    public String deleteActivityStat(String stat_id) {
        activityStatRepository.deleteById(stat_id);
        return "Deleted Success";
    }


    public ActivityStat getActivityStat(String stat_id){
        return activityStatRepository.findById(stat_id).get();
    }

    @Override
    public List<ActivityStat> getAllActivityStats(){
        return activityStatRepository.findAll();
    }

}
