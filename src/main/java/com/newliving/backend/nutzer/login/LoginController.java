package com.newliving.backend.nutzer.login;

import com.newliving.backend.nutzer.login.request.LoginDatenRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Schnittstellen für Login und Logout.
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * Schnittstelle zum Login. Loggt den Nutzer ein und liefert ein Cookie zurück.
     *
     * @param httpServletResponse Kommt vom Spring Boot Framework, genutzt zum Cookie zurückliefern.
     * @param request Request mit Login Daten: Email, Passwort
     * @return true, wenn Daten korrekt, false wenn nicht korrekt
     */
    @PostMapping("/login")
    public boolean login(HttpServletResponse httpServletResponse, @RequestBody LoginDatenRequest request) {
        return loginService.login(httpServletResponse, request);
    }

    /**
     * Schnittstelle zum Logout. Loggt den Nutzer aus, indem der Cookie in der Datenbank gelöscht wird.
     *
     * @param cookieId Der Cookie vom eingeloggten Nutzer
     * @return true, wenn Cookie vorhanden, exception wenn nicht vorhanden
     */
    @RequestMapping("/ausloggen")
    public boolean logout(@CookieValue(name = "JSESSIONID") String cookieId) {
        return loginService.logout(cookieId);
    }

}
