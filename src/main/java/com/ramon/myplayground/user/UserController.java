package com.ramon.myplayground.user;

import com.ramon.myplayground.user.dto.UserRequest;
import com.ramon.myplayground.user.dto.UserResponse;
import com.ramon.myplayground.user.model.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserRequest userRequest) {
        UserEntity user = userService.save(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponse(user.getId(), user.getName(), user.getEmail()));
    }
}
