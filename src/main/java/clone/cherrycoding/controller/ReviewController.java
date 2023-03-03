package clone.cherrycoding.controller;

import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{curriculumId}")
    public void createReview(@PathVariable Long curriculumId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.createReview(curriculumId, requestDto, userDetails);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.updateReview(reviewId, requestDto, userDetails);
    }
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.deleteReview(reviewId, userDetails);
    }
}

