package com.newliving.backend.registrierung;

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
    private final String altPLZ;
    private final String altAdresse;
    private final String neuPLZ;
    private final String neuAdresse;
    private final String iban;
}
