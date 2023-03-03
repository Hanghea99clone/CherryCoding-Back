package clone.cherrycoding.controller;

import clone.cherrycoding.dto.LoginRequestDto;
import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.SignupRequestDto;
import clone.cherrycoding.dto.UserRequestDto;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PutMapping("/{userId}")
    public ResponseDto<String> update(@PathVariable Long userId, @Valid @RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.update(userId, requestDto, userDetails);
    }

    @DeleteMapping("/{userId}")
    public ResponseDto<String> delete(@PathVariable Long userId){
        return userService.delete(userId);
    }
}
