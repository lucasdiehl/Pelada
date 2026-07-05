package com.pelada.api.service.impl;

import com.pelada.api.dto.request.CreateReviewRequest;
import com.pelada.api.dto.response.ReviewResponse;
import com.pelada.api.entity.Match;
import com.pelada.api.entity.Profile;
import com.pelada.api.entity.Review;
import com.pelada.api.entity.User;
import com.pelada.api.enums.MatchStatus;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.mapper.ReviewMapper;
import com.pelada.api.repository.ReviewRepository;
import com.pelada.api.service.MatchService;
import com.pelada.api.service.ProfileService;
import com.pelada.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final MatchService matchService;
    private final ProfileService profileService;

    @Override
    public Double getAverageRating(Profile profile) {
        return reviewRepository.findAverageRating(profile);
    }

    @Override
    public Long getTotalReviews(Profile profile) {
        return reviewRepository.countByTarget(profile);
    }

    public Page<ReviewResponse> getReviews(Profile profile, Pageable pageable) {
        return reviewRepository.findByTarget(profile, pageable).map(reviewMapper::toResponse);
    }

    @Override
    @Transactional
    public void createReview(Long matchId, User user, CreateReviewRequest request) {
        Match match = matchService.getMatch(matchId);
        if (match.getStatus() != MatchStatus.COMPLETED) {
            throw new BusinessException("Only completed matches can be reviewed.");
        }

        Profile author = profileService.getProfileById(user.getId());
        Profile target = profileService.getProfileById(request.targetUserId());

        Review review = Review.builder()
                .match(match)
                .author(author)
                .target(target)
                .rating(request.rating())
                .comment(request.comment())
                .build();

        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
