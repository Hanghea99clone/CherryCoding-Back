package clone.cherrycoding.controller;

import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.EnrollService;
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

    @PostMapping("/curriculum/{curriculumId}")
    public ResponseDto<String> enroll(@PathVariable Long curriculumId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return enrollService.enroll(curriculumId, userDetails.getUser());
    }
}
