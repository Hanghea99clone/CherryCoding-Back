package clone.cherrycoding.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String userId;
    private String username;
    private Boolean isAdmin;

    public static LoginResponseDto of(String username, String nickname, boolean isAdmin) {
        return LoginResponseDto.builder()
                .userId(username)
                .username(nickname)
                .isAdmin(isAdmin)
                .build();
    }
}
