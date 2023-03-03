package clone.cherrycoding.service;

import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.Review;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.entity.UserRoleEnum;
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
    private final LectureRepository LectureRepository;
    @Transactional
    public void createReview(Long curriculumId, ReviewRequestDto requestDto, UserDetailsImpl userDetails) {
        Lecture lecture = LectureRepository.findById(curriculumId).orElseThrow(NullPointerException::new);
        Review review = new Review(
                requestDto.getReviewTitle(), requestDto.getReviewContent(), lecture, userDetails.getUser());

        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NullPointerException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NullPointerException::new);

        checkRole(user, userDetails);
        review.update(requestDto);
    }

    @Transactional
    public void deleteReview(Long reviewId, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(NullPointerException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NullPointerException::new);

        checkRole(user, userDetails);
        reviewRepository.deleteById(reviewId);
    }


    public void checkRole(User user, UserDetails userDetails) {
        if((user.getRole() == UserRoleEnum.ADMIN) || (userDetails.getUsername() == user.getUsername())){
            return;
        }
        throw new IllegalArgumentException("해당 유저만 수정/삭제 가능합니다.");
    }
}
