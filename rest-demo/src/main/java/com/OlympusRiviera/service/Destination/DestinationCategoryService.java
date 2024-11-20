package com.OlympusRiviera.service.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.model.Destination.DestinationCategory;

import java.util.List;

public interface DestinationCategoryService {

    public String createDestinationCategory(DestinationCategory destinationCategory);
    public String updateDestinationCategory(DestinationCategory destinationCategory);
    public String deleteDestinationCategory(String category_id);
    public DestinationCategory getDestinationCategory(String category_id);
    public List<DestinationCategory> getAllDestinationCategories();
}
