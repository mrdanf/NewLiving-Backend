package com.newliving.backend.tipp;

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
public class Tipp {

    @Id
    @SequenceGenerator(name = "tipp_sequence", sequenceName = "tipp_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipp_sequence")
    @Column(name = "tipp_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String text;

    public Tipp(String name, String text) {
        this.name = name;
        this.text = text;
    }

}
