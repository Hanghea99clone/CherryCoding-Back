package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Lecture;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurriculumResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Boolean isEnrolled;
    private int price;
    private int reviewCnt;
    private int studentCnt;

//    private List<ReviewResponseDto> reviewList;

    public static CurriculumResponseDto of(Lecture lecture) {
        return CurriculumResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .imageUrl(lecture.getImageUrl())
                .price(lecture.getPrice())
                .reviewCnt(lecture.getReviewList().size())
                .studentCnt(lecture.getEnrollList().size())
//                .reviewList()
                .build();
    }

    public void setEnrolled(Boolean enrolled) {
        isEnrolled = enrolled;
    }

    //    public List<ReviewResponseDto> getReviewList() {
//        return reviewList;
//    }
}
