package com.OlympusRiviera.repository.Review;

import com.OlympusRiviera.model.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
