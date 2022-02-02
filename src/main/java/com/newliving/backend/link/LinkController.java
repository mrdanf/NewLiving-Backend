package com.newliving.backend.link;

import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.link.request.GetListResponse;
import com.newliving.backend.link.request.OfferHelpRequest;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Schnittstellen für Features mit Link. Darin sind Erstellen eines Links, deaktivieren oder teilen, und auch bei
 * Erhalt des Links das Einsehen der Liste und Eintragen seines Namens.
 */
@RestController
@RequestMapping("/api/link")
@AllArgsConstructor
public class LinkController {

    private final LinkService linkService;

    /**
     * Schnittstelle um einen Link zu erstellen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return den erstellten Link, wenn eingeloggt, sonst exception
     */
    @GetMapping("/neu")
    public String createLink(@CookieValue(name = "JSESSIONID") String cookieId) {
        return linkService.create(cookieId);
    }

    /**
     * Schnittstelle um einen Link zu deaktivieren.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return true wenn eingeloggt, sonst exception
     */
    @GetMapping("/deaktivieren")
    public boolean deactivateLink(@CookieValue(name = "JSESSIONID") String cookieId) {
        return linkService.deactivate(cookieId);
    }

    /**
     * Schnittstelle um einen Link durch die App zu teilen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param emails Liste von Emails, an welche die Einladung gesendet werden soll
     * @return true wenn eingeloggt, sonst exception
     */
    @PostMapping("/teilen")
    public boolean shareLink(@CookieValue(name = "JSESSIONID") String cookieId, JSONArray emails) {
        return linkService.share(cookieId, emails);
    }

    /**
     * Schnittstelle um die Planung eines Freundes abzurufen.
     *
     * @param link_id Id vom Link, welcher auf die geteilte Planung verweist
     * @return true wenn Link existiert, sonst exception
     */
    @GetMapping("/id/{link_id}")
    public GetListResponse getAllEintragFriend(@PathVariable String link_id) {
        return linkService.getAllEintragFriend(link_id);
    }

    /**
     * Schnittstelle um sich als Helfer einzutragen.
     *
     * @param link_id Id vom Link, welcher auf die geteilte Planung verweist
     * @param request Request: eintragId, name
     * @return true wenn Link und Eintrag existieren, sonst exception
     */
    @PostMapping("/id/{link_id}/eintrag")
    public boolean offerHelpEintrag(@PathVariable String link_id, @RequestBody OfferHelpRequest request) {
        return linkService.offerHelpEintrag(link_id, request);
    }
}
