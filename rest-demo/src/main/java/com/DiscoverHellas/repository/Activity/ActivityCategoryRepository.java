package com.DiscoverHellas.repository.Activity;

import com.DiscoverHellas.model.Activity.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, String> {
}
