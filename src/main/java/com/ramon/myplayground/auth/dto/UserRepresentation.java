package com.ramon.myplayground.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRepresentation {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String enabled;
    private credential credentials;
}
