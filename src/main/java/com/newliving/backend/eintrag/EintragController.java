package com.newliving.backend.eintrag;

import com.newliving.backend.eintrag.request.CreateOrUpdateEintragRequest;
import com.newliving.backend.link.helfer.Helfer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Schnittstelle zum Umgang mit Checklist Einträgen. Diese Klasse bietet an: alle Anzeigen, einzelnes genauer
 * Anzeigen, Erstellen, Erledigt setzen, Eintrag aktualisieren, Löschen
 */
@RestController
@RequestMapping("/api/eintrag")
@AllArgsConstructor
public class EintragController {

    private final EintragService eintragService;

    /**
     * Schnittstelle um Daten aller Einträge zu laden.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von Einträgen, sonst exception
     */
    @GetMapping("")
    public List<Eintrag> getAllEintrag(@CookieValue(name = "JSESSIONID") String cookieId) {
        return eintragService.getAll(cookieId);
    }

    /**
     * Schnittstelle um Daten eines Eintrags zu laden.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return Eintrag, sonst exception
     */
    @GetMapping("/id")
    public Eintrag getEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @RequestParam Long id) {
        return eintragService.getOne(cookieId, id);
    }

    /**
     * Schnittstelle um eingetragene Helfer eines Eintrags zu laden.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return Liste von Helfer, sonst exception
     */
    @GetMapping("/helfer")
    public List<Helfer> getEintragHelfer(@CookieValue(name = "JSESSIONID") String cookieId, @RequestParam Long id) {
        return eintragService.getHelfer(cookieId, id);
    }

    /**
     * Schnittstelle um einen Eintrag zu erstellen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param request Request: text, datum
     * @return true wenn eingeloggt, sonst exception
     */
    @PostMapping("/neu")
    public boolean createEintrag(@CookieValue(name = "JSESSIONID") String cookieId,
                                 @RequestBody CreateOrUpdateEintragRequest request) {
        return eintragService.create(cookieId, request);
    }

    /**
     * Schnittstelle um einen Eintrag als Erledigt zu markieren. Kann auch Erledigt entfernen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return true wenn eingeloggt und Eintrag existiert, sonst exception wenn nicht eingeloggt oder false wenn
     * nicht authentifiziert
     */
    @GetMapping("/erledigt")
    public boolean switchEintragErledigt(@CookieValue(name = "JSESSIONID") String cookieId, @RequestParam Long id) {
        return eintragService.switchErledigt(cookieId, id);
    }

    /**
     * Schnittstelle um einen Eintrag zu aktualisieren.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @param request Request: text, datum
     * @return true wenn eingeloggt und Datum korrekt, sonst exception wenn nicht eingeloggt oder false wenn nicht
     * authentifiziert
     */
    @PutMapping("/update")
    public boolean updateEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @RequestParam Long id,
                                 @RequestBody CreateOrUpdateEintragRequest request) {
        return eintragService.update(cookieId, id, request);
    }

    /**
     * Schnittstelle um einen Eintrag zu löschen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return true wenn eingeloggt und Eintrag vorhanden, sonst exception wenn nicht eingeloggt oder false wenn
     * nicht authentifiziert
     */
    @DeleteMapping("/löschen")
    public boolean deleteEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @RequestParam Long id) {
        return eintragService.delete(cookieId, id);
    }

}
