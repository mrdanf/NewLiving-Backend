package com.newliving.backend.nutzer;

import com.newliving.backend.eintrag.EintragService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NutzerService {

    private final NutzerRepository nutzerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EintragService eintragService;

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

    public void resetPasswort(Nutzer nutzer) {
        nutzerRepository.updatePasswortById(nutzer.getId(), nutzer.getPasswort());
    }

    public Nutzer getNutzer(String email) {
        Optional<Nutzer> nutzer = nutzerRepository.findByEmail(email);
        if (nutzer.isPresent()) {
            return nutzer.get();
        }

        throw new IllegalStateException("User existiert nicht.");
    }

    public void setCookie(Long id, String cookie) {
        nutzerRepository.updateCookieById(id, cookie);
    }

    public Nutzer getNutzerByCookie(String cookieId) {
        return nutzerRepository.findNutzerByCookieId(cookieId).get();
    }

    public boolean deleteCookie(String cookieId) {
        Nutzer nutzer = getNutzerByCookie(cookieId);
        nutzer.setCookieId(null);
        nutzerRepository.save(nutzer);
        return true;
    }
}
