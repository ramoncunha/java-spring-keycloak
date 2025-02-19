package com.ramon.myplayground.user;

import com.ramon.myplayground.auth.AuthGateway;
import com.ramon.myplayground.user.dto.UserRequest;
import com.ramon.myplayground.user.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthGateway authGateway;
    @InjectMocks
    private UserService unit;

    @Test
    void save_givenUserRequest_shouldSaveUser() {
        var user = new UserRequest("John Doe", "john@app.com", "123");
        var expected = Mockito.mock(UserEntity.class);

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(expected);

        UserEntity actual = unit.save(user);

        Assertions.assertThat(actual).isEqualTo(expected);

        Mockito.verify(authGateway).saveUser(user);
    }
}
