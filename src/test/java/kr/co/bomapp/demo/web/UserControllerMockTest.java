package kr.co.bomapp.demo.web;

import kr.co.bomapp.demo.domain.user.User;
import kr.co.bomapp.demo.service.UserService;
import kr.co.bomapp.demo.web.dto.UserListResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMockTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void User_조회한다() throws Exception {
        String name = "Eungjoo";
        String email = "eungjoo.kim@gmail.com";

        UserListResponse response = new UserListResponse(
                User.builder()
                    .name("Eungjoo")
                    .email("eungjoo.kim@gmail.com")
                    .build());

        when(userService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.email", is(email)));

        assertThat(outputCapture.toString())
                .contains("findById");
    }
}
