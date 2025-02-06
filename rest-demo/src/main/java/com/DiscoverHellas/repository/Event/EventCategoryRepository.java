package com.DiscoverHellas.repository.Event;

import com.DiscoverHellas.model.Event.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, String> {
}
