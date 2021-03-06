package com.newliving.backend.eintrag;

import com.newliving.backend.nutzer.Nutzer;
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
public class Eintrag {

    @Id
    @SequenceGenerator(name = "eintrag_sequence", sequenceName = "eintrag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eintrag_sequence")
    @Column(name = "eintrag_id")
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean erledigt;

    @Column(nullable = false)
    private Boolean vorgabe;

    private String datum;

    @ManyToOne
    @JoinColumn(nullable = true, name = "nutzer_id")
    private Nutzer nutzer;

    public Eintrag(String text, Nutzer nutzer) {
        this(text, false, null, nutzer);
    }

    public Eintrag(String text, String datum, Nutzer nutzer) {
        this(text, false, datum, nutzer);
    }

    public Eintrag(String text, Boolean vorgabe, Nutzer nutzer) {
        this(text, vorgabe, null, nutzer);
    }

    private Eintrag(String text, Boolean vorgabe, String datum, Nutzer nutzer) {
        this.text = text;
        this.erledigt = false;
        this.vorgabe = vorgabe;
        this.datum = datum;
        this.nutzer = nutzer;
    }

    public void switchErledigt() {
        this.erledigt = !erledigt;
    }
}
