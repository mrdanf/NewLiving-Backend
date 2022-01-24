package com.newliving.backend.registrierung;

import com.newliving.backend.email.EmailService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrierungService {

    private final NutzerService nutzerService;
    private final EmailService emailService;

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

    public boolean resetPasswort(String email) {
        Nutzer nutzer = nutzerService.getNutzer(email);
        String tempPasswort = createRandomPasswort(nutzer);
        nutzer.setPasswort(tempPasswort);
        nutzerService.resetPasswort(nutzer);
        emailService.send(nutzer.getEmail(), emailService.buildEmailPasswortReset(nutzer.getName(), tempPasswort));

        return true;
    }

    private String createRandomPasswort(Nutzer nutzer) {
        String passwort = UUID.randomUUID().toString().replaceAll("-", "");

        return nutzer.getName() + passwort;
    }
}
