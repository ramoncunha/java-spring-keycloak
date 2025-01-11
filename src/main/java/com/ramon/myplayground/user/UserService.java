package com.ramon.myplayground.user;

import com.ramon.myplayground.auth.AuthGateway;
import com.ramon.myplayground.user.dto.UserRequest;
import com.ramon.myplayground.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthGateway authGateway;

    public UserEntity save(UserRequest userRequest) {
        authGateway.saveUser(userRequest);

        UserEntity user = UserEntity.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .build();
        return userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return null;
    }

    public UserEntity findById(UUID id) {
        return null;
    }

    public UserEntity update(UUID id, UserRequest userRequest) {
        return null;
    }

    public void delete(UUID id) {

    }
}
