package com.newliving.backend.registrierung;

import com.newliving.backend.email.EmailService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

/**
 * Zuständig für Registrierung und Passwort zurücksetzen.
 */
@Service
@AllArgsConstructor
public class RegistrierungService {

    private final NutzerService nutzerService;
    private final EmailService emailService;

    /**
     * Registriert den Nutzer.
     *
     * Der Nutzer wird erstellt, solange die Email noch nicht verwendet wurde. Nach der Erstellung wird eine Email zur
     * Bestätigung versendet.
     *
     * @param request Request mit Registrierungsdaten: Email, Name, Passwort. Rest (Adresse und IBAN) ist optional
     * @return true, wenn die Email nicht besetzt ist, sonst exception
     */
    public boolean register(RegistrierungRequest request) {
        Nutzer nutzer;

        nutzer = new Nutzer(request.getEmail(), request.getName(),
                request.getPasswort(), request.getAltPLZ(), request.getAltAdresse(), request.getNeuPLZ(),
                request.getNeuAdresse(), request.getIban());

        boolean status = nutzerService.signUpNutzer(nutzer);

        if (status) {
            emailService.send(request.getEmail(), emailService.buildEmailRegistration(request.getName()));
        }

        return status;
    }

    /**
     * Setzt das Passwort des Nutzers zurück und vergibt ein zufälliges neues.
     *
     * @param email Email von dem dazugehörigen Account
     *
     * @return true, wenn die Email existiert und das Zurücksetzen geklappt hat, sonst exception
     */
    public boolean resetPasswort(String email) {
        Nutzer nutzer = nutzerService.getNutzer(email);
        String tempPasswort = createRandomPasswort();
        nutzer.setPasswort(tempPasswort);
        nutzerService.resetPasswort(nutzer);
        emailService.send(nutzer.getEmail(), emailService.buildEmailPasswortReset(nutzer.getName(), tempPasswort));

        return true;
    }

    private String createRandomPasswort() {
        return RandomString.make(16);
    }
}
