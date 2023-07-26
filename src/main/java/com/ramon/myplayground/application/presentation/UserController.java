package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.infrastructure.dtos.UserRequest;
import com.ramon.myplayground.infrastructure.dtos.UserResponse;
import com.ramon.myplayground.application.services.IUserService;
import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserRequest userRequest) {
        UserEntity user = userService.save(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponse(user.getId(), user.getName(), user.getEmail()));
    }
}
