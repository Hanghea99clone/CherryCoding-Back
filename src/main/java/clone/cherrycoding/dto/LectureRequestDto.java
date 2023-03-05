package clone.cherrycoding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LectureRequestDto {

    @Schema
    private String title;
    @Schema
    private String content;
    @Schema
    private int price;
}
