package clone.cherrycoding.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @NotBlank(message="아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z[0-9]]{4,10}$",
            message = "아이디는 영문 소문자와 숫자가 적어도 1개이상씩 포함된 4 ~ 10자의 아이디여야 합니다.")
    private String username;

    @NotBlank(message="패스워드는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8 ~ 15자의 비밀번호여야 합니다.")
    private String password;

    @Size(min = 2, max = 10,
            message = "닉네임은 2 ~ 10자의 닉네임이여야 합니다.")
    private String nickname;

    private String email;
    private String adminToken;

    private boolean admin;
}
