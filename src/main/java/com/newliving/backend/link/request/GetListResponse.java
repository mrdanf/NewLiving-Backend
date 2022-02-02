package com.newliving.backend.link.request;

import com.newliving.backend.eintrag.Eintrag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GetListResponse {

    private String name;
    private List<Eintrag> eintragList;

}
