package com.newliving.backend.nutzer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Nutzer {

    @Id
    @SequenceGenerator(name = "nutzer_sequence", sequenceName = "nutzer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nutzer_sequence")
    private Long id;

    @Column(nullable = false)
    @NaturalId
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String passwort;

    private String link;

    private String altPLZ;

    private String altAdresse;

    private String neuPLZ;

    private String neuAdresse;

    private String iban;

    private String cookieId;

    public Nutzer(String email, String name, String passwort) {
        this(email, name, passwort, null, null, null, null, null);
    }

    public Nutzer(String email, String name, String passwort, String altPLZ, String altAdresse, String neuPLZ,
                  String neuAdresse, String iban) {
        this.email = email;
        this.name = name;
        this.passwort = passwort;
        this.altPLZ = altPLZ;
        this.altAdresse = altAdresse;
        this.neuPLZ = neuPLZ;
        this.neuAdresse = neuAdresse;
        this.iban = iban;
    }

    /*

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Nutzer)) return false;
        return id != null && id.equals(((Nutzer) obj).getId());
    }

    */

}
