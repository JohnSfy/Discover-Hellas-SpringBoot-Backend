package com.OlympusRiviera.repository.Event;

import com.OlympusRiviera.model.Event.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, String> {
}
