package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Lecture;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    public static LectureResponseDto of(Lecture lecture) {
        return LectureResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .imageUrl(lecture.getImageUrl())
                .build();
    }
}
