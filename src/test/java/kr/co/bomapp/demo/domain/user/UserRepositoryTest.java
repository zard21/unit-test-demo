package kr.co.bomapp.demo.domain.user;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void User_조회한다() {
        String name = "eungjoo";
        String email = "eungjoo.kim@gmail.com";

        userRepository.save(User.builder()
            .name(name)
            .email(email)
            .build());

        List<User> userList = userRepository.findAll();

        User user = userList.get(0);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}
