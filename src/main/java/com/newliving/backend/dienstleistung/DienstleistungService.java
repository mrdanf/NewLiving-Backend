package com.newliving.backend.dienstleistung;

import com.newliving.backend.dienstleistung.angebot.Angebot;
import com.newliving.backend.dienstleistung.angebot.AngebotService;
import com.newliving.backend.dienstleistung.osm.RestService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import com.newliving.backend.nutzer.login.CheckLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zuständig für das Holen aller Dienstleistungen, Sortieren und Filtern und auch buchen.
 */
@Service
@AllArgsConstructor
public class DienstleistungService {

    private final DienstleistungRepository dienstleistungRepository;
    private final CheckLoginService checkLoginService;
    private final NutzerService nutzerService;
    private final AngebotService angebotService;
    private final RestService restService;

    /**
     * Liefert eine Liste von allen Dienstleistungen zurück, wenn der Nutzer eingeloggt ist.
     *
     * Der Nutzer muss eingeloggt sein und seine Adressen müssen hinterlegt sein, damit die Liste von
     * Dienstleistungen angezeigt wird. Das kommt daher, dass der Service nicht umsonst für jede Person zugreifbar
     * sein soll, und ungefähre Preisanalysen für den Nutzer erstellt werden sollen anhand seiner Daten.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von allen Dienstleistungen, wenn Nutzer eingeloggt und Daten angegeben
     */
    public List<Angebot> getAll(String cookieId) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);

            if (isAddressEmpty(nutzer)) {
                return null;
            }

            List<Angebot> angebots = angebotService.getAll();

            for (Angebot angebot : angebots) {
                String[] altLonLat = restService.getAdresseLonLat(nutzer.getAltPLZ(), nutzer.getAltAdresse());

                String[] neuLonLat = restService.getAdresseLonLat(nutzer.getNeuPLZ(), nutzer.getNeuAdresse());

                String[] routingInfo = restService.getRoutingInfo(altLonLat, neuLonLat);

                Double distanceInKm = Double.parseDouble(routingInfo[0]) / 1000;
                Double durationInH = Double.parseDouble(routingInfo[1]) / 3600;
                Double kaution = angebots.get(0).getKaution();

                Double gesamt = kaution + distanceInKm * angebots.get(0).getPreisProKilometer();

                angebots.get(0).setGesamtPreis(gesamt);
            }

        }

        return null;
    }

    public Angebot getOne(String cookieId, Long id) {
        return null;
    }

    private boolean isAddressEmpty(Nutzer nutzer) {
        String altPLZ = nutzer.getAltPLZ();
        String altAdresse = nutzer.getAltAdresse();
        String neuPLZ = nutzer.getNeuPLZ();
        String neuAdresse = nutzer.getNeuAdresse();

        if (altPLZ == null || altAdresse == null || neuPLZ == null || neuAdresse == null) {
            // Keine Kontaktdaten angegeben
            return true;
        }
        return false;
    }

    public List<Dienstleistung> getByType(String cookieId, String art) {
        // TODO
        if (art.equalsIgnoreCase("anhänger")) {
            System.out.println("ANHÄNGER ignore case");
        }
        return null;
    }

    public List<Dienstleistung> getSorted(String cookieId, String art) {
        // TODO
        if (art.equalsIgnoreCase("gesamt")) {
            System.out.println("gesamt ignore case");
        } else if (art.equalsIgnoreCase("kilometer")) {
            System.out.println("KILOMETER IGNORE CASE");
        }
        return null;
    }

    public boolean book(String cookieId, Long id) {
        // TODO
        return false;
    }
}
