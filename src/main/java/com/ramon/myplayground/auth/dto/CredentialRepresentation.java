package com.ramon.myplayground.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CredentialRepresentation {
    private String type;
    private String value;
    private boolean temporary;
}
