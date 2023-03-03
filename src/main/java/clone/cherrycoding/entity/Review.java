package clone.cherrycoding.entity;

import clone.cherrycoding.dto.ReviewRequestDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewTitle;

    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Review(String reviewTitle, String reviewContent, Lecture lecture, User user) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.lecture = lecture;
        this.user = user;
    }

    public void update(ReviewRequestDto requestDto){
        this.reviewTitle = requestDto.getReviewTitle();
        this.reviewContent = requestDto.getReviewContent();
    }
}
