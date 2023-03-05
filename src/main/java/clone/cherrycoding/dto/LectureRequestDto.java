package clone.cherrycoding.dto;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class LectureRequestDto {

    private String title;
    private String content;
    private int price;
    @Nullable
    private MultipartFile multipartFile;
}
