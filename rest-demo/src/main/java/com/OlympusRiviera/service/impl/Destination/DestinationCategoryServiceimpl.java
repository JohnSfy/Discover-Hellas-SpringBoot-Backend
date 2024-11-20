package com.OlympusRiviera.service.impl.Destination;

import com.OlympusRiviera.model.Destination.DestinationCategory;
import com.OlympusRiviera.repository.Destination.DestinationCategoryRepository;
import com.OlympusRiviera.service.Destination.DestinationCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationCategoryServiceimpl implements DestinationCategoryService {
    DestinationCategoryRepository destinationCategoryRepository;

    public DestinationCategoryServiceimpl(DestinationCategoryRepository destinationCategoryRepository) {
        this.destinationCategoryRepository = destinationCategoryRepository;
    }

    public String createDestinationCategory(DestinationCategory destinationCategory) {
        destinationCategoryRepository.save(destinationCategory);
        return "Success";
    }

    @Override
    public String updateDestinationCategory(DestinationCategory destinationCategory) {
        // More logic
        destinationCategoryRepository.save(destinationCategory);
        return "Update Success";
    }

    public String deleteDestinationCategory(String category_id) {
        destinationCategoryRepository.deleteById(category_id);
        return "Deleted Success";
    }

    @Override
    public DestinationCategory getDestinationCategory(String category_id) {
        return destinationCategoryRepository.findById(category_id).get();
    }

    public List<DestinationCategory> getAllDestinationCategories() {
        return destinationCategoryRepository.findAll();
    }
}
