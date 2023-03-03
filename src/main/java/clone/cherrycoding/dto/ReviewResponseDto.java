package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private Long id;
    private String reviewTitle;
    private String reviewContent;
    private String nickname;
    private LocalDateTime modifiedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.nickname = review.getUser().getNickname();
        this.modifiedAt = review.getModifiedAt();
    }

}
