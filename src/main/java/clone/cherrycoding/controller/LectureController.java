package clone.cherrycoding.controller;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.security.UserDetailsServiceImpl;
import clone.cherrycoding.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    @Operation(summary = "홈페이지")
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        return lectureService.getLecture();
    }

    @GetMapping("/curriculum")
    @Operation(summary = "교육과정 모든강좌")
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy) {
        return lectureService.getCurriculum(page - 1, size, sortBy, userDetailsService.getUser());
    }

    @GetMapping("/curriculum/{curriculumId}")
    @Operation(summary = "강좌 상세페이지")
    public ResponseDto<DetailResponseDto> getDetail(@PathVariable Long curriculumId) {

        return lectureService.getDetail(curriculumId, userDetailsService.getUser());
    }

    @GetMapping("/user-curriculum")
    @Operation(summary = "교육과정 내 강좌")
    public ResponseDto<List<CurriculumResponseDto>> getUserCurriculum() {

        return lectureService.getUserCurriculum(userDetailsService.getUser());
    }

    @PostMapping(value = "/lecture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "강좌 추가")
    public ResponseDto<String> addLecture(@RequestPart LectureRequestDto requestDto,
                                          @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {

        return lectureService.add(requestDto, multipartFile, userDetailsService.getUser());
    }

    @PutMapping(value = "/curriculum/{curriculumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "강좌 수정")
    public ResponseDto<String> updateLecture(@PathVariable Long curriculumId,
                                             @RequestPart LectureRequestDto requestDto,
                                             @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {

        return lectureService.update(curriculumId, requestDto, multipartFile, userDetailsService.getUser());
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    @Operation(summary = "강좌 삭제")
    public ResponseDto<String> deleteLecture(@PathVariable Long curriculumId) {
        return lectureService.delete(curriculumId, userDetailsService.getUser());
    }
}
