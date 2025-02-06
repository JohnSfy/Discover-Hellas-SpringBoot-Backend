package com.DiscoverHellas.repository.Activity;

import com.DiscoverHellas.model.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String>{
    
}
