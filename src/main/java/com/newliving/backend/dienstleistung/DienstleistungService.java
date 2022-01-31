package com.newliving.backend.dienstleistung;

import com.newliving.backend.dienstleistung.osm.RestService;
import com.newliving.backend.email.EmailService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import com.newliving.backend.nutzer.login.CheckLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
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
    private final RestService restService;
    private final EmailService emailService;

    /**
     * Liefert eine Liste von allen Dienstleistungen zurück, wenn der Nutzer eingeloggt ist.
     * <p>
     * Der Nutzer muss eingeloggt sein und seine Adressen müssen hinterlegt sein, damit die Liste von
     * Dienstleistungen angezeigt wird. Das kommt daher, dass der Service nicht umsonst für jede Person zugreifbar
     * sein soll, und ungefähre Preisanalysen für den Nutzer erstellt werden sollen anhand seiner Daten.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von allen Dienstleistungen, wenn Nutzer eingeloggt und Daten angegeben, sonst null
     */
    public List<Dienstleistung> getAll(String cookieId) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            // Nutzer ist eingeloggt
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);

            if (isAddressEmpty(nutzer)) {
                return null;
            }

            List<Dienstleistung> dienstleistungs = dienstleistungRepository.findAll();

            calculateGesamtPreis(nutzer, dienstleistungs);
            sortByGesamtPreis(dienstleistungs);

            return dienstleistungs;
        }

        return null;
    }

    public Dienstleistung getOne(String cookieId, Long id) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
            if (isAddressEmpty(nutzer)) {
                return null;
            }

            return dienstleistungRepository.findById(id).get();

        }
        return null;
    }

    public List<Dienstleistung> getByType(String cookieId, String art) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
            if (isAddressEmpty(nutzer)) {
                return null;
            }

            List<Dienstleistung> dienstleistungsByType = null;
            if (art.equalsIgnoreCase("anhänger")) {
                dienstleistungsByType = dienstleistungRepository.findAllByTypContaining("Anhänger");
            } else if (art.equalsIgnoreCase("transporter")) {
                dienstleistungsByType = dienstleistungRepository.findAllByTypContaining("Transporter");
            }

            calculateGesamtPreis(nutzer, dienstleistungsByType);
            sortByGesamtPreis(dienstleistungsByType);

            return dienstleistungsByType;
        }

        return null;
    }

    public List<Dienstleistung> getSorted(String cookieId, String art) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
            if (isAddressEmpty(nutzer)) {
                return null;
            }

            List<Dienstleistung> dienstleistungsSorted = dienstleistungRepository.findAll();
            calculateGesamtPreis(nutzer, dienstleistungsSorted);

            if (art.equalsIgnoreCase("gesamt")) {
                sortByGesamtPreis(dienstleistungsSorted);
            } else if (art.equalsIgnoreCase("kilometer")) {
                dienstleistungsSorted.sort(new Comparator<Dienstleistung>() {
                    @Override
                    public int compare(Dienstleistung o1, Dienstleistung o2) {
                        Double o1PreisProKilometer = o1.getPreisProKilometer();
                        Double o2PreisProKilometer = o2.getPreisProKilometer();

                        return o1PreisProKilometer.compareTo(o2PreisProKilometer);
                    }
                });
            } else if (art.equalsIgnoreCase("stunde")) {
                dienstleistungsSorted.sort(new Comparator<Dienstleistung>() {
                    @Override
                    public int compare(Dienstleistung o1, Dienstleistung o2) {
                        Double o1PreisProStunde = o1.getPreisProStunde();
                        Double o2PreisProStunde = o2.getPreisProStunde();

                        return o1PreisProStunde.compareTo(o2PreisProStunde);
                    }
                });
            }

            return dienstleistungsSorted;
        }
        return null;
    }

    private void sortByGesamtPreis(List<Dienstleistung> dienstleistungsSorted) {
        dienstleistungsSorted.sort(new Comparator<Dienstleistung>() {
            @Override
            public int compare(Dienstleistung o1, Dienstleistung o2) {
                Double o1Gesamt = o1.getGesamtPreis();
                Double o2Gesamt = o2.getGesamtPreis();

                return o1Gesamt.compareTo(o2Gesamt);
            }
        });
    }

    public boolean book(String cookieId, Long id) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
            if (isAddressEmpty(nutzer)) {
                return false;
            }

            Dienstleistung dienstleistung = dienstleistungRepository.findById(id).get();
            calculateGesamtPreis(nutzer, List.of(dienstleistung));
            emailService.send(nutzer.getEmail(), emailService.buildEmailBook(nutzer.getName(), dienstleistung));

            return true;
        }

        return false;
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

    private void calculateGesamtPreis(Nutzer nutzer, List<Dienstleistung> dienstleistungs) {
        String[] distanceDuration = getDistanceDuration(nutzer);

        Double distanceInKm = Double.parseDouble(distanceDuration[0]) / 1000;
        Double durationInH = Double.parseDouble(distanceDuration[1]) / 3600;

        for (Dienstleistung dienstleistung : dienstleistungs) {

            Double kaution = dienstleistung.getKaution();

            Double gesamt =
                    kaution + distanceInKm * dienstleistung.getPreisProKilometer() + durationInH * dienstleistung.getPreisProStunde();

            gesamt = round(gesamt, 2);
            dienstleistung.setGesamtPreis(gesamt);
        }
    }

    private String[] getDistanceDuration(Nutzer nutzer) {
        String[] altLonLat = restService.getAdresseLonLat(nutzer.getAltPLZ(), nutzer.getAltAdresse());

        String[] neuLonLat = restService.getAdresseLonLat(nutzer.getNeuPLZ(), nutzer.getNeuAdresse());

        return restService.getRoutingInfo(altLonLat, neuLonLat);
    }

    /**
     * Rundet Double auf {places} stellen nach dem Komma. Von stackoverflow: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     *
     * @param value Double Wert der gerundet wird
     * @param places Anzahl an Stellen, auf die gerundet werden soll
     * @return Double Zahl gerundet
     */
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
