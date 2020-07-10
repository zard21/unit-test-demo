package kr.co.bomapp.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bomapp.demo.web.dto.UserSaveRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void User_등록한다() throws Exception {
        String name = "Eungjoo";
        String email = "eungjoo.kim@bomapp.co.kr";

        UserSaveRequest request = UserSaveRequest.builder()
                .name(name)
                .email(email)
                .build();

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
