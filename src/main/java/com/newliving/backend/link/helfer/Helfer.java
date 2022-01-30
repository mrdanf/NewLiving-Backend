package com.newliving.backend.link.helfer;

import com.newliving.backend.eintrag.Eintrag;
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
public class Helfer {

    @Id
    @SequenceGenerator(name = "helfer_sequence", sequenceName = "helfer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "helfer_sequence")
    @Column(name = "helfer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = true, name = "eintrag_id")
    private Eintrag eintrag;

    public Helfer(String name, Eintrag eintrag) {
        this.name = name;
        this.eintrag = eintrag;
    }
}
