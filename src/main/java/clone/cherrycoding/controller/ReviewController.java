package clone.cherrycoding.controller;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.repository.LectureRepository;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{curriculumId}")
    @Operation(summary = "댓글 작성")
    public ResponseDto<String> createReview(@PathVariable Long curriculumId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.createReview(curriculumId, requestDto, userDetails);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "댓글 수정")
    public ResponseDto<String> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.updateReview(reviewId, requestDto, userDetails);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "댓글 삭제")
    public ResponseDto<String> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.deleteReview(reviewId, userDetails);
    }
}

