package clone.cherrycoding.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자명은 4~10자리까지만 가능합니다. ")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "비밀번호는 8~15자리까지만 가능합니다.")
    private String password;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자리까지만 가능합니다. ")
    private String nickname;

    private String email;
    private String adminToken;

    private boolean isAdmin;
}
