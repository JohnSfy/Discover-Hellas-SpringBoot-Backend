package com.DiscoverHellas.service.Review;

import com.DiscoverHellas.model.Review.Review;

import java.util.List;

public interface ReviewService {

    public String createReview(Review review);
    public String updateReview(Review review);
    public String deleteReview(String review_id);
    public Review getReview(String review_id);
    public List<Review> getAllReviews();
}
