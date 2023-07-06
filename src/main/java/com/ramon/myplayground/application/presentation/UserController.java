package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.application.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private IUserService userService;

}
