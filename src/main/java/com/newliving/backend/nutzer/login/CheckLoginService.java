package com.newliving.backend.nutzer.login;

import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Nur zur Überprüfung des Login Status.
 */
@Service
@AllArgsConstructor
public class CheckLoginService {

    private final NutzerRepository nutzerRepository;

    /**
     * Überprüft, ob der Nutzer eingeloggt ist.
     *
     * Wenn sich der Nutzer erfolgreich eingeloggt hat, wird ein Cookie in der Datenbank für den Nutzer
     * eingespeichert. Das Frontend liefert diesen Cookie bei jeder Abfrage zurück und dieser wird dann mit dem in
     * der Datenbank vorhandenen Cookie abgeglichen.
     *
     * @param cookieId Der Cookie vom eingeloggten Nutzer beim Frontend
     * @return true, wenn echter Nutzer eingeloggt, false wenn Cookie nicht dem Nutzer zuordenbar
     */
    public boolean checkLoggedIn(String cookieId) {
        Optional<Nutzer> nutzerOpt = nutzerRepository.findNutzerByCookieId(cookieId);
        if (nutzerOpt.isPresent()) {
            // Cookie ist in der Datenbank vorhanden -> Nutzer ist eingeloggt
            return true;
        } else {
            throw new IllegalStateException("Falsche Logindaten oder nicht erlaubter Zugriff!");
        }
    }
}
