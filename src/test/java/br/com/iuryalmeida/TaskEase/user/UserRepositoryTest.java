package br.com.iuryalmeida.TaskEase.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserModel userModel = new UserModel();
        userModel.setUsername("testuser");
        userModel.setPassword("password");

        UserModel savedUser = userRepository.save(userModel);
        assertNotNull(savedUser.getId());
    }
}