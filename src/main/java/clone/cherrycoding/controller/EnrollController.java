package clone.cherrycoding.controller;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.security.UserDetailsServiceImpl;
import clone.cherrycoding.service.EnrollService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollService enrollService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/curriculum/{curriculumId}")
    @Operation(summary = "수강 신청")
    public ResponseDto<String> enroll(@PathVariable Long curriculumId) {
        return enrollService.enroll(curriculumId, userDetailsService.getUser());
    }
}
