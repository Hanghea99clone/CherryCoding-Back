package clone.cherrycoding.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String username;
    private Boolean isAdmin;

    public static LoginResponseDto of(String nickname, boolean isAdmin) {
        return LoginResponseDto.builder()
                .username(nickname)
                .isAdmin(isAdmin)
                .build();
    }
}
