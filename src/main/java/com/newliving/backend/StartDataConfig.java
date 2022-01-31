package com.newliving.backend;

import com.newliving.backend.dienstleistung.Dienstleistung;
import com.newliving.backend.dienstleistung.DienstleistungRepository;
import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.eintrag.EintragRepository;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerRepository;
import com.newliving.backend.tipp.Tipp;
import com.newliving.backend.tipp.TippRepository;
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
                                 DienstleistungRepository dienstleistungRepository, TippRepository tippRepository) {
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
                    5.0
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Autoverleih Speed",
                    "32584 Löhne, Herforder Straße 32",
                    "Transporter",
                    150.00,
                    0.32,
                    13.50
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Möbel Gustav",
                    "32545 Bad Oeynhausen, Hahnenkampstraße 27",
                    "Anhänger",
                    30.00,
                    0.27,
                    3.75
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Möbel Gustav",
                    "32545 Bad Oeynhausen, Hahnenkampstraße 27",
                    "Transporter",
                    100.00,
                    0.30,
                    11.30
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Baustoffe Meyer",
                    "32427 Minden, Marienstraße 35",
                    "Anhänger",
                    40.00,
                    0.15,
                    4.50
            ));

            dienstleistungs.add(new Dienstleistung(
                    "Baustoffe Meyer",
                    "32427 Minden, Marienstraße 35",
                    "Transporter",
                    80.00,
                    0.35,
                    9.50
            ));

            dienstleistungRepository.saveAll(dienstleistungs);

            // Tipp
            List<Tipp> tipps = new ArrayList<>();
            tipps.add(new Tipp(
                    "Geschirr einpacken",
                    "Beim Geschirr einpacken muss man sehr vorsichtig sein. Da Geschirr meistens aus Porzellan und " +
                            "Glas besteht, muss man darauf achten, dass das Geschirr nicht beim Umzug zerbricht. Um " +
                            "das besser zu verhindern, sollte man das Geschirr immer in schützendes Material " +
                            "einwickeln. Dazu kann man Handtücher oder Zeitungspapier verwenden. Zeitungspapier ist " +
                            "optimal, da es ein gutes Verpackungsmaterial ist, welches oft sowieso jede Woche " +
                            "kostenlos im Briefkasten liegt."
            ));

            tipps.add(new Tipp(
                    "Große Müllbeutel bereitlegen",
                    "Bei einem Umzug fällt immer sehr viel Müll an, auch wenn man nicht viel neue Möbel oder " +
                            "Gegenstände dazu kauft. Oft sind es aussortierte alte Gegenstände, die z.B. kaputt sind " +
                            "oder nicht mehr gebraucht werden. Verpackungsmaterial von z.B. Geschirr häuft sich auch " +
                            "an, sowie Kreppband für Möbel oder Plastiktüten. Manchmal steht in der neuen Wohnung der" +
                            " Mülleimer noch nicht bereit, daher können Müllbeutel auch normalen Haushaltsmüll fürs " +
                            "erste entsorgen."
            ));

            tipps.add(new Tipp(
                    "Kartons beschriften",
                    "Um den Überblick über die gepackten Kartons zu behalten, sollten Kartons immer beschriftet " +
                            "werden. Dazu kann man z.B. einen Karton mit \"Küche, Geschirr\" oder \"Wohnzimmer: " +
                            "Sideboard\" beschriften. Dadurch wissen alle Helfer sofort, in welchen Raum der Karton " +
                            "muss, außerdem ist das auspacken und einräumen der Kartons sehr viel einfacher."
            ));

        };

    }

}
