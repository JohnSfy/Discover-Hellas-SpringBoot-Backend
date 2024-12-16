package com.OlympusRiviera.service.Event;

import com.OlympusRiviera.model.Event.Event;

import java.util.List;

public interface EventService {

    public String createEvent(Event event);
    public String updateEvent(Event event);
    public String deleteEvent(String event_id);
    public Event getEvent(String event_id);
    public List<Event> getAllEvents();
}
