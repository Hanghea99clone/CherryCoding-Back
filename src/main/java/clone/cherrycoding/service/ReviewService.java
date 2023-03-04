package clone.cherrycoding.service;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.Review;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.entity.UserRoleEnum;
import clone.cherrycoding.repository.LectureRepository;
import clone.cherrycoding.repository.ReviewRepository;
import clone.cherrycoding.repository.UserRepository;
import clone.cherrycoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public ResponseDto<String> createReview(Long curriculumId, ReviewRequestDto requestDto, UserDetailsImpl userDetails) {
        Lecture lecture = lectureRepository.findById(curriculumId).orElseThrow(NullPointerException::new);
        lecture.review(lecture.getReviewCnt() + 1);
        Review review = new Review(
                requestDto.getReviewTitle(), requestDto.getReviewContent(), lecture, userDetails.getUser());

        reviewRepository.save(review);
        return ResponseDto.success("댓글 작성 완료");
    }

    @Transactional
    public ResponseDto<String> updateReview(Long reviewId, ReviewRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NullPointerException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NullPointerException::new);

        checkRole(user, userDetails);
        review.update(requestDto);
        return ResponseDto.success("댓글 수정 완료");
    }

    @Transactional
    public ResponseDto<String> deleteReview(Long reviewId, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NullPointerException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NullPointerException::new);
        review.getLecture().review(review.getLecture().getReviewCnt() - 1);
        checkRole(user, userDetails);
        reviewRepository.deleteById(reviewId);
        return ResponseDto.success("댓글 삭제 완료");
    }


    public void checkRole(User user, UserDetails userDetails) {
        if((user.getRole() == UserRoleEnum.ADMIN) || (userDetails.getUsername() == user.getUsername())){
            return;
        }
        throw new IllegalArgumentException("해당 유저만 수정/삭제 가능합니다.");
    }
}
