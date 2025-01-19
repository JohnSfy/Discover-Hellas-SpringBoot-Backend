package com.OlympusRiviera.repository.Activity;

import com.OlympusRiviera.model.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String>{
    
}
