package com.ramon.myplayground.user;

import com.ramon.myplayground.user.dto.UserRequest;
import com.ramon.myplayground.user.dto.UserResponse;
import com.ramon.myplayground.user.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController unit;

    @Test
    void saveUser_givenValidUser_shouldSaveUser() {
        var request = Mockito.mock(UserRequest.class);
        var entity = Mockito.mock(UserEntity.class);
        var UUIDMock = Mockito.mock(UUID.class);

        Mockito.when(userService.save(request)).thenReturn(entity);
        Mockito.when(entity.getId()).thenReturn(UUIDMock);
        Mockito.when(entity.getName()).thenReturn("John Doe");
        Mockito.when(entity.getEmail()).thenReturn("john@email.com");

        ResponseEntity<UserResponse> actual = unit.saveUser(request);

        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(actual.getBody())
                .usingRecursiveComparison()
                .isEqualTo(new UserResponse(UUIDMock, "John Doe", "john@email.com"));
    }
}
