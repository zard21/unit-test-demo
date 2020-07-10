package kr.co.bomapp.demo.web.dto;

import kr.co.bomapp.demo.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequest {

    private String name;
    private String email;

    @Builder
    public UserSaveRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
