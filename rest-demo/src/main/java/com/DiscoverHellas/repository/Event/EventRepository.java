package com.DiscoverHellas.repository.Event;

import com.DiscoverHellas.model.Event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
