package com.OlympusRiviera.repository.Event;

import com.OlympusRiviera.model.Event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
