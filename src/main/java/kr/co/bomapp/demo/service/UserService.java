package kr.co.bomapp.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.bomapp.demo.domain.user.User;
import kr.co.bomapp.demo.domain.user.UserRepository;
import kr.co.bomapp.demo.web.dto.UserListResponse;
import kr.co.bomapp.demo.web.dto.UserSaveRequest;
import kr.co.bomapp.demo.web.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserListResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserListResponse findById(Long id) {
        System.out.println(">>> findById <<<");

        return userRepository.findById(id).map(UserListResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다. id=" + id));
    }

    @Transactional
    public Long save(UserSaveRequest userSaveRequest) {
        return userRepository.save(userSaveRequest.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 사용자가 없습니다. id=" + id));

        user.update(userUpdateRequest.getName(), userUpdateRequest.getEmail());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 사용자가 없습니다. id=" + id));

        userRepository.delete(user);
    }
}
