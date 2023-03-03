package clone.cherrycoding.service;

import clone.cherrycoding.dto.LoginRequestDto;
import clone.cherrycoding.dto.ResponseDto;
import clone.cherrycoding.dto.SignupRequestDto;
import clone.cherrycoding.dto.UserRequestDto;
import clone.cherrycoding.entity.User;
import clone.cherrycoding.entity.UserRoleEnum;
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
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> foundUser = userRepository.findByUsername(signupRequestDto.getUsername());

        // 예외처리 커스텀 할것
        if(foundUser.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                // 예외처리 커스텀 할것
                throw new IllegalArgumentException("관리자 암호가 틀립니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(new User(signupRequestDto, role));

//        return ResponseDto.success(); // 반환 어떻게 할건지 결정
    }

    public void login(LoginRequestDto loginRequestDto){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        responseHeaders.set("Authorization",token);
//        responseHeaders.add("userId", user.getId().toString());

    }

    @Transactional
    public void update(Long userId, UserRequestDto requestDto, UserDetailsImpl userDetails) {
        String nickname = requestDto.getNickname();
        String newPw = passwordEncoder.encode(requestDto.getNewPw());

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        //admin 인지, 유저 아이디가 일치하는지 확인
        checkRole(user, userDetails);

        // 관리자일 경우 비밀번호를 몰라도, 수정 가능
//        if(user.getRole() == UserRoleEnum.ADMIN){
//            user.update(UserRequestDto);
//        }
        //기존 패스워드와 가져온 패스워드 비교
        if(passwordEncoder.matches(requestDto.getPassword(), newPw)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.update(requestDto);
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public void checkRole(User user, UserDetails userDetails) {
        if((user.getRole() == UserRoleEnum.ADMIN) || (userDetails.getUsername() == user.getUsername())){
            return;
        }
        throw new IllegalArgumentException("해당 유저만 수정/삭제 가능합니다.");
    }
}
