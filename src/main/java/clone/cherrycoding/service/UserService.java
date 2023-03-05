package clone.cherrycoding.service;

import clone.cherrycoding.dto.*;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.entity.UserRoleEnum;
import clone.cherrycoding.exception.CustomException;
import clone.cherrycoding.exception.ErrorCode;
import clone.cherrycoding.jwt.JwtUtil;
import clone.cherrycoding.repository.UserRepository;
import clone.cherrycoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<String> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> foundUsername = userRepository.findByUsername(signupRequestDto.getUsername());

        // 예외처리 커스텀 할것
        if(foundUsername.isPresent()){
            throw new CustomException(ErrorCode.DuplicateUsername);
        }

        Optional<User> foundNickname = userRepository.findByNickname(signupRequestDto.getNickname());
        if(foundNickname.isPresent()){
            throw new CustomException(ErrorCode.DuplicatedNickname);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                // 예외처리 커스텀 할것
                throw new CustomException(ErrorCode.NotMatchAdminPassword);
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(new User(username, password, signupRequestDto.getEmail(), signupRequestDto.getNickname(), role));

        return ResponseDto.success("회원가입 성공");
    }

    @Transactional
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(LoginRequestDto loginRequestDto){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new CustomException(ErrorCode.NotFoundUser));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.NotMatchPassword);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        responseHeaders.set("Authorization",token);

        boolean isAdmin = user.getRole() == UserRoleEnum.ADMIN;

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(ResponseDto.success(LoginResponseDto.of(user.getNickname(), isAdmin)));
    }

    @Transactional
    public ResponseDto<String> update(UserRequestDto requestDto, User user) {
        String dtoPw = passwordEncoder.encode(requestDto.getPassword());
        String newPw = passwordEncoder.encode(requestDto.getNewPw());

        //기존 패스워드와 가져온 패스워드 비교
        if(!passwordEncoder.matches(dtoPw, user.getPassword())){
            throw new CustomException(ErrorCode.NotMatchPassword);
        }

        if(passwordEncoder.matches(dtoPw, newPw)) {
            throw new CustomException(ErrorCode.NotAllowSamePassword);
        }

        user.update(newPw);
        return ResponseDto.success("회원정보 수정 완료");
    }

    @Transactional
    public ResponseDto<String> delete(Long userId) {
        userRepository.deleteById(userId);

        return ResponseDto.success("회원탈퇴 성공");
    }

    public void checkRole(User user, UserDetails userDetails) {
        if((user.getRole() == UserRoleEnum.ADMIN) || (userDetails.getUsername() == user.getUsername())){
            return;
        }
        throw new CustomException(ErrorCode.NoPermission);
    }
}
