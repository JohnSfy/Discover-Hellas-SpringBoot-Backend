package com.DiscoverHellas.service.impl.Review;

import com.DiscoverHellas.model.Review.Review;
import com.DiscoverHellas.repository.Review.ReviewRepository;
import com.DiscoverHellas.service.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceimpl implements ReviewService {

    ReviewRepository reviewRepository;

    public ReviewServiceimpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Implement the methods for interacting with the review repository
    @Override
    public String createReview(Review review) {
        reviewRepository.save(review);
        return "Success";
    }

    @Override
    public String updateReview(Review review) {
//        more logic
        reviewRepository.save(review);
        return "Update Success";
    }

    @Override
    public String deleteReview(String review_id) {
        reviewRepository.deleteById(review_id);
        return "Deleted Success";
    }

    @Override
    public Review getReview(String review_id) {

        return reviewRepository.findById(review_id).get();
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
