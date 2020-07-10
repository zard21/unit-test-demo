package kr.co.bomapp.demo.web;

import java.util.List;
import kr.co.bomapp.demo.service.UserService;
import kr.co.bomapp.demo.web.dto.UserListResponse;
import kr.co.bomapp.demo.web.dto.UserSaveRequest;
import kr.co.bomapp.demo.web.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public List<UserListResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/api/v1/users/{id}")
    public UserListResponse findById(@PathVariable Long id) {
        System.out.println(">>> findById <<<");

        return userService.findById(id);
    }

    @PostMapping("/api/v1/users")
    public Long save(@RequestBody UserSaveRequest userSaveRequest) {
        return userService.save(userSaveRequest);
    }

    @PutMapping("/api/v1/users/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public Long delete(@PathVariable Long id) {
        userService.delete(id);

        return id;
    }
}
