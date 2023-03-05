package clone.cherrycoding.controller;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.security.UserDetailsServiceImpl;
import clone.cherrycoding.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/{curriculumId}")
    @Operation(summary = "댓글 작성")
    public ResponseDto<String> createReview(@PathVariable Long curriculumId, @RequestBody ReviewRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetailsService.getUser(userDetails);

        return reviewService.createReview(curriculumId, requestDto, user);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "댓글 수정")
    public ResponseDto<String> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetailsService.getUser(userDetails);

        return reviewService.updateReview(reviewId, requestDto, user);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "댓글 삭제")
    public ResponseDto<String> deleteReview(@PathVariable Long reviewId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetailsService.getUser(userDetails);

        return reviewService.deleteReview(reviewId, user);
    }
}

