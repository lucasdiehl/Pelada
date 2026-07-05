package com.pelada.api.mapper;

import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getAuthor().getFullName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
