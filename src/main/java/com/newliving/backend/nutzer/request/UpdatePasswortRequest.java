package com.newliving.backend.nutzer.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatePasswortRequest {
    private final String altPasswort;
    private final String neuPasswort;
}
