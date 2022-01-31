package com.newliving.backend;

import com.newliving.backend.dienstleistung.Dienstleistung;
import com.newliving.backend.dienstleistung.DienstleistungRepository;
import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.eintrag.EintragRepository;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class StartDataConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner appUserCLR(NutzerRepository nutzerRepository, EintragRepository eintragRepository,
                                 DienstleistungRepository dienstleistungRepository) {
        return args -> {

            // Nutzer
            List<Nutzer> nutzers = new ArrayList<>();
            nutzers.add(new Nutzer(
                    "maxmuster@gmail.com",
                    "Max Mustermann",
                    bCryptPasswordEncoder.encode("abc"),
                    "32545",
                    "Mindenerstraße 10",
                    "32584",
                    "Lübbeckerstraße 20",
                    "DE0998753123"
            ));

            nutzers.get(0).setLink("maxmustertestlink");

            nutzers.add(new Nutzer(
                    "peter@gmail.com",
                    "Peter Petermann",
                    bCryptPasswordEncoder.encode("password")
            ));

            nutzerRepository.saveAll(nutzers);

            // Eintrag
            List<Eintrag> eintrags = new ArrayList<>();
            eintrags.add(new Eintrag(
                    "Müllbeutel kaufen",
                    true,
                    nutzers.get(0)
            ));

            eintrags.add(new Eintrag(
                    "Müllbeutel kaufen",
                    true,
                    nutzers.get(1)
            ));

            eintrags.add(new Eintrag(
                    "Wickelpapier für Geschirr besorgen",
                    true,
                    nutzers.get(0)
            ));

            eintrags.add(new Eintrag(
                    "Wickelpapier für Geschirr besorgen",
                    true,
                    nutzers.get(1)
            ));

            eintrags.add(new Eintrag(
                    "Küche einpacken",
                    "22.02.2022",
                    nutzers.get(0)
            ));

            eintrags.add(new Eintrag(
                    "Keller aufräumen",
                    nutzers.get(0)
            ));

            eintragRepository.saveAll(eintrags);

            // Dienstleistung
            List<Dienstleistung> dienstleistungs = new ArrayList<>();
            dienstleistungs.add(new Dienstleistung(
                    "Autoverleih Speed",
                    "32584 Löhne, Herforder Straße 32",
                    "Anhänger",
                    50.00,
                    0.20,
                    2.0
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Autoverleih Speed",
                    "32584 Löhne, Herforder Straße 32",
                    "Transporter",
                    150.00,
                    0.22,
                    8.0
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Möbel Gustav",
                    "32545 Bad Oeynhausen, Hahnenkampstraße 27",
                    "Anhänger",
                    30.00,
                    0.27,
                    2.75
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Möbel Gustav",
                    "32545 Bad Oeynhausen, Hahnenkampstraße 27",
                    "Transporter",
                    100.00,
                    0.30,
                    9.30
            ));

            dienstleistungRepository.saveAll(dienstleistungs);
        };

    }

}
