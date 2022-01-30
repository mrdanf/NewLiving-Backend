package com.newliving.backend.dienstleistung;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Dienstleistung {

    @Id
    @SequenceGenerator(name = "dienstleistung_sequence", sequenceName = "dienstleistung_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstleistung_sequence")
    @Column(name = "dienstleistung_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String anschrift;
}
