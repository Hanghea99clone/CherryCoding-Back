package clone.cherrycoding.controller;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.security.UserDetailsServiceImpl;
import clone.cherrycoding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입")
    public ResponseDto<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PutMapping("/update")
    @Operation(summary = "회원정보 수정")
    public ResponseDto<String> update(@Valid @RequestBody UserRequestDto requestDto,@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetailsService.getUser(userDetails);

        return userService.update(requestDto, user);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴")
    public ResponseDto<String> delete(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetailsService.getUser(userDetails);

        return userService.delete(user.getId());
    }
}
