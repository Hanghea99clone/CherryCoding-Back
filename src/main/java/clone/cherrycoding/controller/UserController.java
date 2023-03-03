package clone.cherrycoding.controller;

import clone.cherrycoding.dto.LoginRequestDto;
import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.SignupRequestDto;
import clone.cherrycoding.dto.UserRequestDto;
import clone.cherrycoding.security.UserDetailsImpl;
import clone.cherrycoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
//        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.update(userId, requestDto, userDetails);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId){
        userService.delete(userId);
    }
}
