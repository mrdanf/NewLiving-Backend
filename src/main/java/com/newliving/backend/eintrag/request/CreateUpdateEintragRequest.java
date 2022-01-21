package com.newliving.backend.eintrag.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateUpdateEintragRequest {
    private final String text;
    private final String datum;
}
