package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Lecture;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    @Builder
    public LectureResponseDto(Long id, String title, String content, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static LectureResponseDto of(Lecture lecture) {
        return LectureResponseDto.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .content(lecture.getContent())
                .imageUrl(lecture.getImageUrl())
                .build();
    }
}
