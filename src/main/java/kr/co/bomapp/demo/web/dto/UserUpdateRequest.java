package kr.co.bomapp.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;
    private String email;

    @Builder
    public UserUpdateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
