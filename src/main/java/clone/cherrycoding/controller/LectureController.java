package clone.cherrycoding.controller;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.security.UserDetailsServiceImpl;
import clone.cherrycoding.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    @Operation(summary = "홈페이지")
    public ResponseDto<List<LectureResponseDto>> getLecture() {
        return lectureService.getLecture();
    }

    @GetMapping("/curriculum")
    @Operation(summary = "교육과정 모든강좌", description = "sortBy = createdAt, reviewCnt, enrollCnt")
    public ResponseDto<List<CurriculumResponseDto>> getCurriculum(@RequestParam int page,
                                                                  @RequestParam int size,
                                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                                  @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.getCurriculum(page - 1, size, sortBy, user);
    }

    @GetMapping("/curriculum/{curriculumId}")
    @Operation(summary = "강좌 상세페이지")
    public ResponseDto<DetailResponseDto> getDetail(@PathVariable Long curriculumId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.getDetail(curriculumId, user);
    }

    @GetMapping("/user-curriculum")
    @Operation(summary = "교육과정 내 강좌")
    public ResponseDto<List<CurriculumResponseDto>> getUserCurriculum(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.getUserCurriculum(user);
    }

    @PostMapping(value = "/lecture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "강좌 추가")
    public ResponseDto<String> addLecture(@RequestPart LectureRequestDto requestDto,
                                          @RequestPart(value = "image") MultipartFile multipartFile,
                                          @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.add(requestDto, multipartFile, user);
    }

    @PutMapping(value = "/curriculum/{curriculumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "강좌 수정")
    public ResponseDto<String> updateLecture(@PathVariable Long curriculumId,
                                             @RequestPart LectureRequestDto requestDto,
                                             @RequestPart(value = "image") MultipartFile multipartFile,
                                             @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.update(curriculumId, requestDto, multipartFile, user);
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    @Operation(summary = "강좌 삭제")
    public ResponseDto<String> deleteLecture(@PathVariable Long curriculumId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetailsService.getUser(userDetails);

        return lectureService.delete(curriculumId, user);
    }
}
