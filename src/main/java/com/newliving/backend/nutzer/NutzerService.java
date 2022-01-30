package com.newliving.backend.nutzer;

import com.newliving.backend.eintrag.EintragService;
import com.newliving.backend.nutzer.request.UpdateDatenRequest;
import com.newliving.backend.nutzer.request.UpdatePasswortRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Zuständig für das Holen und Erstellen, Setzen, Updaten oder Löschen von Nutzer und Nutzer Cookies.
 */
@Service
@AllArgsConstructor
public class NutzerService {

    private final NutzerRepository nutzerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Überprüft Existenz von Nutzer und liefert ihn zurück.
     * <p>
     * Überprüft, ob der Nutzer mit dieser Email Adresse bereits existiert. Falls der Nutzer existiert, wird dieser
     * zurückgeliefert.
     *
     * @param email Email des Nutzeraccounts
     * @return Nutzer, wenn er existiert, andernfalls exception
     */
    public Nutzer getNutzer(String email) {
        Optional<Nutzer> nutzer = nutzerRepository.findByEmail(email);
        if (nutzer.isPresent()) {
            return nutzer.get();
        }

        throw new IllegalStateException("Nutzer existiert nicht.");
    }

    /**
     * Setzt den Cookie für einen Nutzer in der Datenbank nach erfolgreichem Login.
     *
     * @param id     Nutzer ID
     * @param cookie Cookie, der gesetzt werden soll und an Frontend zurückgeliefert wird
     */
    public void setCookie(Long id, String cookie) {
        nutzerRepository.updateCookieById(id, cookie);
    }

    /**
     * Liefert den eingeloggten Nutzer anhand Cookie zurück. Diese Methode wird nur aufgerufen, sobald ein Nutzer
     * bereits eingeloggt ist. Wenn der Nutzer nicht eingeloggt ist, wird diese Methode eine exception schmeißen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Nutzer, der eingeloggt ist, sonst exception
     */
    public Nutzer getNutzerByCookie(String cookieId) {
        return nutzerRepository.findNutzerByCookieId(cookieId).get();
    }

    /**
     * Registriert den Nutzer in der Datenbank, solange die Email nicht bereits vergeben ist.
     * <p>
     * Registriert einen neuen Nutzer in der Datenbank. Falls die Email bereits vergeben ist, wird eine exception
     * geworfen. Das Passwort wird mit BCryptPasswordEncoder verschlüsselt in der Datenbank abgespeichert.
     *
     * @param nutzer Nutzer, der neu erstellt wird
     * @return true, wenn Email nicht vergeben war, sonst exception
     */
    public boolean signUpNutzer(Nutzer nutzer) {
        boolean userExists = nutzerRepository.findByEmail(nutzer.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Email schon vergeben!");
        }

        String encodedPasswort = bCryptPasswordEncoder.encode(nutzer.getPasswort());

        nutzer.setPasswort(encodedPasswort);

        nutzerRepository.save(nutzer);

        return true;
    }

    /**
     * Setzt das Passwort vom Nutzer zurück und vergibt ein zufälliges neues.
     *
     * @param nutzer Nutzer, bei dem Passwort zurückgesetzt wird
     */
    public void resetPasswort(Nutzer nutzer) {
        nutzerRepository.updatePasswortById(nutzer.getId(), nutzer.getPasswort());
    }

    /**
     * Loggt den Nutzer aus, indem der Cookie in der Datenbank gelöscht wird.
     *
     * @param cookieId Cookie, der gelöscht werden soll
     * @return true, wenn der Nutzer eingeloggt war, sonst exception
     */
    public boolean deleteCookie(String cookieId) {
        Nutzer nutzer = getNutzerByCookie(cookieId);
        nutzer.setCookieId(null);
        nutzerRepository.save(nutzer);
        return true;
    }

    /**
     * Ändert das Passwort des Nutzers.
     * <p>
     * Der Nutzer gibt sein altes und sein neues Passwort ein. Wenn das alte Passwort mit seinem aktuellen Passwort
     * übereinstimmt, wird das aktuelle Passwort auf das neue Passwort nach Verschlüsselung geändert.
     *
     * @param cookieId Cookie des eingeloggten Nutzers
     * @param request Request zum Passwort ändern (altPasswort, neuPasswort)
     * @return true, wenn das Passwort korrekt ist, sonst false
     */
    public boolean updatePasswort(String cookieId, UpdatePasswortRequest request) {
        Nutzer nutzer = getNutzerByCookie(cookieId);
        if (nutzerAuthenticated(nutzer, request.getAltPasswort())) {
            String neuPasswort = bCryptPasswordEncoder.encode(request.getNeuPasswort());
            nutzerRepository.updatePasswortById(nutzer.getId(), neuPasswort);

            return true;
        } else {
            return false;
        }
    }

    private boolean nutzerAuthenticated(Nutzer nutzer, String passwort) {
        // Überprüfe Authentifizierung, das angegebene Passwort muss mit dem aktuellen Passwort übereinstimmen
        String currentPasswort = nutzer.getPasswort();
        return bCryptPasswordEncoder.matches(passwort, currentPasswort);
    }

    /**
     * Ändert die Daten des Nutzers.
     *
     * Ändert die Daten des Nutzers, wenn der Nuzter eingeloggt ist und die neue Email Adresse nicht bereits vergeben
     * ist. Die Methode überprüft dazu, welche Daten das Frontend mitgegeben hat und ändert die Daten anhand der
     * eingehenden Daten aus dem Frontend.
     *
     * @param cookieId Cookie des eingeloggten Nutzers
     * @param request Request zum Daten ändern (name, email, altPLZ, altAdresse, neuPLZ, neuAdresse, iban)
     * @return true, wenn die Email Adresse nicht vergeben oder nicht angegeben war, sonst false wenn die Email vergeben
     * war
     */
    public boolean updateDaten(String cookieId, UpdateDatenRequest request) {
        Nutzer nutzer = getNutzerByCookie(cookieId);
        String neuEmail = request.getEmail();
        if (nutzerRepository.findByEmail(neuEmail).isEmpty()) {
            String name = nutzer.getName();
            String email = nutzer.getEmail();
            String altPLZ = nutzer.getAltPLZ();
            String altAdresse = nutzer.getAltAdresse();
            String neuPLZ = nutzer.getNeuPLZ();
            String neuAdresse = nutzer.getNeuAdresse();
            String iban = nutzer.getIban();

            if (!name.equals("")) {
                nutzer.setName(name);
            }
            if (!email.equals("")) {
                nutzer.setEmail(email);
            }
            if (!altPLZ.equals("")) {
                nutzer.setAltPLZ(altPLZ);
            }
            if (!altAdresse.equals("")) {
                nutzer.setAltAdresse(altAdresse);
            }

            if (!neuPLZ.equals("")) {
                nutzer.setNeuPLZ(neuPLZ);
            }

            if (!neuAdresse.equals("")) {
                nutzer.setNeuAdresse(neuAdresse);
            }

            if (!iban.equals("")) {
                nutzer.setIban(iban);
            }

            nutzerRepository.save(nutzer);
            return true;
        }
        // Neue Email ist bereits vergeben
        return false;
    }

    /**
     * Speichert für andere Klassen eine Änderung am Nutzer und kapselt somit die Kommunikation mit NutzerRepository ab.
     *
     * @param nutzer Nutzer mit geänderten Daten, der abgespeichert werden soll
     */
    public void save(Nutzer nutzer) {
        nutzerRepository.save(nutzer);
    }

    public Nutzer getNutzerByLink(String link_id) {
        return nutzerRepository.findNutzerByLink(link_id).get();
    }
}
