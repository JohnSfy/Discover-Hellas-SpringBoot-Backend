package com.DiscoverHellas.service.impl.Event;

import com.DiscoverHellas.model.Event.Event;
import com.DiscoverHellas.repository.Event.EventRepository;
import com.DiscoverHellas.service.Event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceimpl implements EventService {

    EventRepository eventRepository;

    public EventServiceimpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Implement the methods for interacting with the Events repository
    @Override
    public String createEvent(Event event) {
        eventRepository.save(event);
        return "Success";
    }

    @Override
    public String updateEvent(Event event) {
//        more logic
        eventRepository.save(event);
        return "Update Success";
    }

    @Override
    public String deleteEvent(String event_id) {
        eventRepository.deleteById(event_id);
        return "Deleted Success";
    }

    @Override
    public Event getEvent(String event_id) {

        return eventRepository.findById(event_id).get();
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
