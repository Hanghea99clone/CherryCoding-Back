package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<ReviewResponseDto> reviewList;

    public static CurriculumResponseDto of(Lecture lecture) {
        return CurriculumResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .imageUrl(lecture.getImageUrl())
                .price(lecture.getPrice())
                .reviewCnt(lecture.getReviewList().size())
                .studentCnt(lecture.getEnrollList().size())
                .reviewList(lecture.getReviewList().stream().sorted((a, b) ->
                                b.getModifiedAt().compareTo(a.getModifiedAt()))
                        .map(ReviewResponseDto::new)
                        .collect(Collectors.toList()))
                .build();
    }

    public void setEnrolled(Boolean enrolled) {
        isEnrolled = enrolled;
    }
}