package com.OlympusRiviera.service.impl.Event;

import com.OlympusRiviera.model.Event.EventCategory;
import com.OlympusRiviera.repository.Event.EventCategoryRepository;
import com.OlympusRiviera.service.Event.EventCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCategoryServiceimpl implements EventCategoryService {
    EventCategoryRepository eventCategoryRepository;

    public EventCategoryServiceimpl(EventCategoryRepository eventCategoryRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
    }

    public String createEventCategory(EventCategory eventCategory) {
        eventCategoryRepository.save(eventCategory);
        return "Success";
    }

    @Override
    public String updateEventCategory(EventCategory eventCategory) {
        // More logic
        eventCategoryRepository.save(eventCategory);
        return "Update Success";
    }

    public String deleteEventCategory(String category_id) {
        eventCategoryRepository.deleteById(category_id);
        return "Deleted Success";
    }

    @Override
    public EventCategory getEventCategory(String category_id) {
        return eventCategoryRepository.findById(category_id).get();
    }

    public List<EventCategory> getAllEventCategories() {
        return eventCategoryRepository.findAll();
    }
}
