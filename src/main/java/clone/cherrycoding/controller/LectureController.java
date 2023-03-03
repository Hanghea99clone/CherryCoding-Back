package clone.cherrycoding.controller;

import clone.cherrycoding.dto.CurriculumResponseDto;
import clone.cherrycoding.dto.LectureRequestDto;
import clone.cherrycoding.dto.LectureResponseDto;
import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/lecture")
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        return lectureService.getLecture();
    }

    @GetMapping("/curriculum")
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = null;
        if (userDetails != null) {
            user = userDetails.getUser();
        }
        return lectureService.getCurriculum(user);
    }

    @PostMapping("/lecture")
    public ResponseDto<String> addLecture(@RequestBody LectureRequestDto requestDto,
                                     @RequestPart(value = "image") MultipartFile multipartFile,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return lectureService.add(requestDto, multipartFile, userDetails.getUser());
    }

    @PutMapping("/curriculum/{curriculumId}")
    public ResponseDto<String> updateLecture(@PathVariable Long curriculumId,
                                             @RequestBody LectureRequestDto requestDto,
                                             @RequestPart(value = "image") MultipartFile multipartFile,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return lectureService.update(curriculumId, requestDto, multipartFile, userDetails.getUser());
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    public ResponseDto<String> deleteLecture(@PathVariable Long curriculumId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return lectureService.delete(curriculumId, userDetails.getUser());
    }
}
