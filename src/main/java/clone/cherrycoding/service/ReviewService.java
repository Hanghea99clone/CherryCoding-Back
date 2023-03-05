package clone.cherrycoding.service;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.ReviewRequestDto;
import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.Review;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.entity.UserRoleEnum;
import clone.cherrycoding.exception.CustomException;
import clone.cherrycoding.exception.ErrorCode;
import clone.cherrycoding.repository.LectureRepository;
import clone.cherrycoding.repository.ReviewRepository;
import clone.cherrycoding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public ResponseDto<String> createReview(Long curriculumId, ReviewRequestDto requestDto, User user) {
        Lecture lecture = lectureRepository.findById(curriculumId).
                orElseThrow(()-> new CustomException(ErrorCode.NotFoundLecture));
        lecture.review(lecture.getReviewCnt() + 1);
        Review review = new Review(
                requestDto.getReviewTitle(), requestDto.getReviewContent(), lecture, user);

        reviewRepository.save(review);
        return ResponseDto.success("댓글 작성 완료");
    }

    @Transactional
    public ResponseDto<String> updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        Review review = reviewRepository.findById(reviewId).
                orElseThrow(()-> new CustomException(ErrorCode.NotFoundReview));

        checkRole(user, review);
        review.update(requestDto);
        return ResponseDto.success("댓글 수정 완료");
    }

    @Transactional
    public ResponseDto<String> deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).
                orElseThrow(()-> new CustomException(ErrorCode.NotFoundReview));
        review.getLecture().review(review.getLecture().getReviewCnt() - 1);

        checkRole(user, review);
        reviewRepository.deleteById(reviewId);
        return ResponseDto.success("댓글 삭제 완료");
    }


    public void checkRole(User user, Review review) {
        if((user.getRole() == UserRoleEnum.ADMIN) || (review.getUser().getUsername().equals(user.getUsername()))){
            return;
        }
        throw new CustomException(ErrorCode.NoPermission);
    }
}
