package com.newliving.backend.nutzer.login;

import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import com.newliving.backend.nutzer.login.request.LoginDatenRequest;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Loggt den Nutzer ein und aus. Beim Einloggen wird ein Cookie angelegt, beim Ausloggen wird es gelöscht.
 */
@Service
@AllArgsConstructor
public class LoginService {

    private final NutzerService nutzerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Loggt den Nutzer ein und liefert Cookie zurück.
     *
     * Die Daten vom Login Request werden mit den Daten in der Datenbank abgeglichen. Das Passwort ist verschlüsselt,
     * also wird mit BCryptPasswordEncoder das Passwort abgeglichen. Wenn alles geklappt hat, wird ein Cookie
     * angelegt und dieses zurück an das Frontend geliefert.
     *
     * @param httpServletResponse Von Spring Boot Framework, nötig für Cookie
     * @param request Request mit Login Daten: Email, Passwort
     * @return true, wenn Daten korrekt, exception wenn nicht korrekt
     */
    public boolean login(HttpServletResponse httpServletResponse, LoginDatenRequest request) {
        String email = request.getEmail();
        String passwort = request.getPasswort();

        Nutzer nutzer = nutzerService.getNutzer(email);
        if (bCryptPasswordEncoder.matches(passwort, nutzer.getPasswort())) {

            String cookie = createCookieId(email);
            nutzerService.setCookie(nutzer.getId(), cookie);
            httpServletResponse.addCookie(new Cookie("JSESSIONID", cookie));

            return true;
        } else {
            throw new IllegalStateException("Passwort falsch.");
        }
    }

    private String createCookieId(String email) {
        StringBuilder result = new StringBuilder(email);
        result.append(RandomString.make());

        return result.toString();
    }

    public boolean logout(String cookieId) {
        return nutzerService.deleteCookie(cookieId);
    }
}
