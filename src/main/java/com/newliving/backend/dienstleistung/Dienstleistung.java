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

    @Column(nullable = false)
    private String typ;

    @Column(nullable = false)
    private Double kaution;

    @Column(nullable = false)
    private Double preisProKilometer;

    @Column(nullable = false)
    private Double preisProStunde;

    private Double gesamtPreis;

    public Dienstleistung(String name, String anschrift, String typ, Double kaution, Double preisProKilometer,
                          Double preisProStunde) {
        this.name = name;
        this.anschrift = anschrift;
        this.typ = typ;
        this.kaution = kaution;
        this.preisProKilometer = preisProKilometer;
        this.preisProStunde = preisProStunde;
    }
}
