package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Lecture;
import clone.cherrycoding.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class LectureResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private List<ReviewResponseDto> reviewList = new ArrayList<>();

    public static LectureResponseDto of(Lecture lecture) {
        return LectureResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .imageUrl(lecture.getImageUrl())
                .build();
    }
}
