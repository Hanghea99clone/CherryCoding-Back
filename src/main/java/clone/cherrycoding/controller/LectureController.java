package clone.cherrycoding.controller;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.security.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping("/lecture")
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        return lectureService.getLecture();
    }

    @GetMapping("/curriculum")
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        final User user = userDetailsService.getUser(userDetails);

        return lectureService.getCurriculum(page - 1, size, sortBy, user);
    }

    @GetMapping("/curriculum/{curriculumId}")
    public ResponseDto<DetailResponseDto> getDetail(@PathVariable Long curriculumId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        final User user = userDetailsService.getUser(userDetails);

        return lectureService.getDetail(curriculumId, user);
    }

    @GetMapping("/user-curriculum")
    public ResponseDto<List<CurriculumResponseDto>> getUserCurriculum(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return lectureService.getUserCurriculum(userDetails.getUser());
    }

    @PostMapping("/lecture")
    public ResponseDto<String> addLecture(@RequestPart LectureRequestDto requestDto,
                                          @RequestPart(value = "image") MultipartFile multipartFile,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return lectureService.add(requestDto, multipartFile, userDetails.getUser());
    }

    @PutMapping("/curriculum/{curriculumId}")
    public ResponseDto<String> updateLecture(@PathVariable Long curriculumId,
                                             @RequestPart LectureRequestDto requestDto,
                                             @RequestPart(value = "image") MultipartFile multipartFile,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return lectureService.update(curriculumId, requestDto, multipartFile, userDetails.getUser());
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    public ResponseDto<String> deleteLecture(@PathVariable Long curriculumId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return lectureService.delete(curriculumId, userDetails.getUser());
    }
}
