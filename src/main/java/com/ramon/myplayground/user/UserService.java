package com.ramon.myplayground.user;

import com.ramon.myplayground.auth.AuthGateway;
import com.ramon.myplayground.user.dto.UserRequest;
import com.ramon.myplayground.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
