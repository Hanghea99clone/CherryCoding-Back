package clone.cherrycoding.dto;

import clone.cherrycoding.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MainResponseDto {
    List<LectureResponseDto> lectureDto = new ArrayList<>();
    List<ReviewResponseDto> reviewDto = new ArrayList<>();

    public MainResponseDto(List<LectureResponseDto> lectureDto, List<Review> reviews) {
        this.lectureDto = lectureDto;

        List<ReviewResponseDto> dto = new ArrayList<>();
        for (Review review : reviews) {
            dto.add(new ReviewResponseDto(review));
        }

        this.reviewDto = dto;
    }
}
