package clone.cherrycoding.entity;

import clone.cherrycoding.dto.SignupRequestDto;
import clone.cherrycoding.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private UserRoleEnum role;

    public User(SignupRequestDto signupRequestDto, UserRoleEnum role) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.email = signupRequestDto.getEmail();
        this.nickname = signupRequestDto.getNickname();
        this.role = role;
    }

    public void update(UserRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getNewPw();
    }
}
