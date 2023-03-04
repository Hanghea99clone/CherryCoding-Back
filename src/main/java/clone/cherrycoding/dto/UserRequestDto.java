package clone.cherrycoding.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class UserRequestDto {
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$", message = "비밀번호는 8~15자리까지만 가능합니다.")
    private String newPw;
}
