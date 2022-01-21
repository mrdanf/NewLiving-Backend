package com.newliving.backend.nutzer.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateDatenRequest {
    private final String name;
    private final String email;
    private final String altPLZ;
    private final String altAdresse;
    private final String neuPLZ;
    private final String neuAdresse;
    private final String iban;
}
