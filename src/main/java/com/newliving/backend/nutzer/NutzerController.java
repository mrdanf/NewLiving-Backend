package com.newliving.backend.nutzer;

import com.newliving.backend.nutzer.request.UpdateDatenRequest;
import com.newliving.backend.nutzer.request.UpdatePasswortRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Schnittstellen für das Holen der Nutzerdaten, Update von Passwort und Update von Daten.
 */
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class NutzerController {

    private final NutzerService nutzerService;

    /**
     * Schnittstelle zum Holen der Nutzerdaten.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return eingeloggter Nutzer, sonst exception
     */
    @GetMapping("")
    public Nutzer getNutzer(@CookieValue(name = "JSESSIONID") String cookieId) {
        return nutzerService.getNutzerByCookie(cookieId);
    }

    /**
     * Schnittstelle zum Aktualisieren von dem Passwort.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param request Request: altPasswort, neuPasswort
     * @return
     */
    @PutMapping("/update-passwort")
    public boolean updatePasswort(@CookieValue(name = "JSESSIONID") String cookieId,
                                  @RequestBody UpdatePasswortRequest request) {
        return nutzerService.updatePasswort(cookieId, request);
    }

    /**
     * Schnittstelle zum Aktualisieren von Nutzerdaten.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param request Request: name, email, altPLZ, altAdresse, neuPLZ, neuAdresse, iban
     * @return
     */
    @PutMapping("/update-daten")
    public boolean updateDaten(@CookieValue(name = "JSESSIONID") String cookieId,
                              @RequestBody UpdateDatenRequest request) {
        return nutzerService.updateDaten(cookieId, request);
    }

}
