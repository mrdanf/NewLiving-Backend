package com.newliving.backend.link.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OfferHelpRequest {
    private final Long eintragId;
    private final String name;
}
