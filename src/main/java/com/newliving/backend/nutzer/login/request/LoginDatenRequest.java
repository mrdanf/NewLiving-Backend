package com.newliving.backend.nutzer.login.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginDatenRequest {
    private final String email;
    private final String passwort;
}
