package com.OlympusRiviera.repository.Destination;

import com.OlympusRiviera.model.Destination.Destination;
import com.OlympusRiviera.model.Destination.DestinationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationCategoryRepository extends JpaRepository<DestinationCategory, String> {
}
