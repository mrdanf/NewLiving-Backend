package com.newliving.backend.nutzer.registrierung;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrierungRequest {
    private final String email;
    private final String name;
    private final String passwort;
}
