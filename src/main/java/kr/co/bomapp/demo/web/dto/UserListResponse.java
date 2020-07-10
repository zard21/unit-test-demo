package kr.co.bomapp.demo.web.dto;

import kr.co.bomapp.demo.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListResponse {

    private Long id;
    private String name;
    private String email;

    public UserListResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
