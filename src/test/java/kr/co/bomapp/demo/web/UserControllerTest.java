package kr.co.bomapp.demo.web;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bomapp.demo.domain.user.User;
import kr.co.bomapp.demo.domain.user.UserRepository;
import kr.co.bomapp.demo.web.dto.UserListResponse;
import kr.co.bomapp.demo.web.dto.UserSaveRequest;
import kr.co.bomapp.demo.web.dto.UserUpdateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    // Web API를 테스트할 때 사용, Spring MVC 테스트의 시작점
    @Autowired
    private MockMvc mockMvc;

    // TestRestTemplate은 내장 톰캣이 실행된 경우 사용 가능 (RANDOM_PORT 혹은 DEFINED_PORT)
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void User_전체_조회한다() throws Exception {
        userRepository.save(User.builder()
                .name("Eungjoo")
                .email("eungjoo@gmail.com")
                .build());

        String url = "http://localhost:" + port + "/api/v1/users";

        ResponseEntity<List<UserListResponse>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserListResponse>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isGreaterThanOrEqualTo(0);

        List<UserListResponse> userListResponses = responseEntity.getBody();

        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(userListResponses.size());
        assertThat(users.get(0).getId()).isEqualTo(userListResponses.get(0).getId());
        assertThat(users.get(0).getName()).isEqualTo(userListResponses.get(0).getName());
        assertThat(users.get(0).getEmail()).isEqualTo(userListResponses.get(0).getEmail());
    }
    
    @Test
    @Transactional
    public void User_등록한다() throws Exception {
        String name = "Eungjoo";
        String email = "eungjoo.kim@bomapp.co.kr";

        UserSaveRequest request = UserSaveRequest.builder()
                .name(name)
                .email(email)
                .build();

//        String url = "http://localhost:" + port + "/api/v1/users";
//        Long id = restTemplate.postForObject(url, request, Long.class);
//
//        System.out.println(">>> ID=" + id + " <<<);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        List<User> userList = userRepository.findAll();
        assertThat(userList.get(0).getName()).isEqualTo(name);
        assertThat(userList.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    @Transactional
    public void User_수정한다() throws Exception {
        User savedUser = userRepository.save(User.builder()
                    .name("Steve")
                    .email("steve@gmail.com")
                    .build());

        Long updateId = savedUser.getId();
        String expectedName = "Steve2";
        String expectedEmail = "steve2@gmail.com";

        UserUpdateRequest request =
                UserUpdateRequest.builder()
                    .name(expectedName)
                    .email(expectedEmail)
                    .build();

        mockMvc.perform(put("/api/v1/users/" + updateId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        List<User> userList = userRepository.findAll();
        assertThat(userList.get(0).getName()).isEqualTo(expectedName);
        assertThat(userList.get(0).getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    @Transactional
    public void User_삭제한다() throws Exception {
        User savedUser = userRepository.save(User.builder()
                .name("Steve")
                .email("steve@gmail.com")
                .build());

        Long deleteId = savedUser.getId();

        mockMvc.perform(delete("/api/v1/users/" + deleteId))
                .andExpect(status().isOk());

        List<User> userList = userRepository.findAll();
        assertThat(userList.size()).isEqualTo(0);
    }
}
