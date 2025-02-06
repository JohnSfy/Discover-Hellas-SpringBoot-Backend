package com.DiscoverHellas.service.Event;

import com.DiscoverHellas.model.Event.Event;

import java.util.List;

public interface EventService {

    public String createEvent(Event event);
    public String updateEvent(Event event);
    public String deleteEvent(String event_id);
    public Event getEvent(String event_id);
    public List<Event> getAllEvents();
}
