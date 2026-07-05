package com.pelada.api.service;

import com.pelada.api.dto.request.CreateReviewRequest;
import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Double getAverageRating(Profile profile);
    Long getTotalReviews(Profile profile);
    Page<ReviewResponse> getReviews(Profile profile, Pageable pageable);
    void createReview(Long matchId, User user, @Valid CreateReviewRequest request);
    void deleteReview(Long reviewId);
}
