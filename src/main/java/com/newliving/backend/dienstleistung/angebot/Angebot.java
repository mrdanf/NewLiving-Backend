package com.newliving.backend.dienstleistung.angebot;

import com.newliving.backend.dienstleistung.Dienstleistung;
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
public class Angebot {

    @Id
    @SequenceGenerator(name = "angebot_sequence", sequenceName = "angebot_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "angebot_sequence")
    @Column(name = "angebot_id")
    private Long id;

    @Column(nullable = false)
    private Double kaution;

    @Column(nullable = false)
    private Double preisProKilometer;

    @Column(nullable = false)
    private Double preisProMinute;

    private Double gesamtPreis;

    @ManyToOne
    @JoinColumn(nullable = true, name = "dienstleistung_id")
    private Dienstleistung dienstleistung;

    public Angebot(Double kaution, Double preisProKilometer, Double preisProMinute, Dienstleistung dienstleistung) {
        this.kaution = kaution;
        this.preisProKilometer = preisProKilometer;
        this.preisProMinute = preisProMinute;
        this.dienstleistung = dienstleistung;
    }
}
