package com.DiscoverHellas.repository.Review;

import com.DiscoverHellas.model.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
