package com.newliving.backend.nutzer;

import com.newliving.backend.eintrag.EintragService;
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
    private final EintragService eintragService;

    /**
     * Überprüft Existenz von Nutzer und liefert ihn zurück.
     *
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
     * @param id Nutzer ID
     * @param cookie Cookie, der gesetzt werden soll und an Frontend zurückgeliefert wird
     */
    public void setCookie(Long id, String cookie) {
        nutzerRepository.updateCookieById(id, cookie);
    }

    /**
     * Liefert den eingeloggten Nutzer anhand Cookie zurück. Diese Methode wird nur aufgerufen, sobald ein Nutzer
     * bereits eingeloggt ist.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Nutzer, der eingeloggt ist
     */
    public Nutzer getNutzerByCookie(String cookieId) {
        return nutzerRepository.findNutzerByCookieId(cookieId).get();
    }

    /**
     * Registriert den Nutzer in der Datenbank, solange die Email nicht bereits vergeben ist.
     *
     * Registriert einen neuen Nutzer in der Datenbank. Falls die Email bereits vergeben ist, wird eine exception
     * geworfen. Das Passwort wird mit BCryptPasswordEncoder verschlüsselt in der Datenbank abgespeichert.
     *
     * @param nutzer Nutzer, der neu erstellt wird
     * @return true, wenn Email nicht vergeben war, ansonsten exception
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
     * @return true, wenn der Nutzer eingeloggt war, ansonsten exception
     */
    public boolean deleteCookie(String cookieId) {
        Nutzer nutzer = getNutzerByCookie(cookieId);
        nutzer.setCookieId(null);
        nutzerRepository.save(nutzer);
        return true;
    }
}
