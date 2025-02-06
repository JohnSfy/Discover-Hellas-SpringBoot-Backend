package com.DiscoverHellas.service.Event;

import com.DiscoverHellas.model.Event.EventCategory;

import java.util.List;

public interface EventCategoryService {

    public String createEventCategory(EventCategory eventCategory);
    public String updateEventCategory(EventCategory eventCategory);
    public String deleteEventCategory(String category_id);
    public EventCategory getEventCategory(String category_id);
    public List<EventCategory> getAllEventCategories();


}
