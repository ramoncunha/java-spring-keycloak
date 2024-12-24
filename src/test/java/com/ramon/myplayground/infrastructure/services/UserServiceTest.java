package com.ramon.myplayground.infrastructure.services;

import com.ramon.myplayground.infrastructure.models.UserRequest;
import com.ramon.myplayground.infrastructure.repositories.UserRepository;
import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
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
    @InjectMocks
    private UserService unit;

    @Test
    void save_givenUserRequest_shouldSaveUser() {
        var user = new UserRequest("John Doe", "john@app.com");
        var expected = Mockito.mock(UserEntity.class);

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(expected);

        UserEntity actual = unit.save(user);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
