package com.DiscoverHellas.repository.Activity;

import com.DiscoverHellas.model.Activity.ActivityStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityStatRepository extends JpaRepository<ActivityStat, String> {
}
