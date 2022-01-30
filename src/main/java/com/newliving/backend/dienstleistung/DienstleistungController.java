package com.newliving.backend.dienstleistung;

import com.newliving.backend.dienstleistung.angebot.Angebot;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Schnittstellen um Dienstleistungen zu betrachten, filtern und buchen.
 */
@RestController
@RequestMapping("/api/dienstleistung")
@AllArgsConstructor
public class DienstleistungController {

    private final DienstleistungService dienstleistungService;

    /**
     * Schnittstelle um alle Dienstleistungen anzuzeigen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von allen Dienstleistungen
     */
    @GetMapping("")
    public List<Angebot> getAllDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId) {
        return dienstleistungService.getAll(cookieId);
    }

    /**
     * Schnittstelle um Dienstleistung nach Art gefiltert anzuzeigen. Filterung nach z.B. Anhänger oder Transporter
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param art Art von Dienstleistung: anhänger | transporter
     * @return gefilterte Liste von Dienstleistungen
     */
    @GetMapping("/art/{art}")
    public List<Dienstleistung> getAllDienstleistungByType(@CookieValue(name = "JSESSIONID") String cookieId,
                                  @PathVariable String art) {
        return dienstleistungService.getByType(cookieId, art);
    }

    /**
     * Schnittstelle um Dienstleistung nach Preisen zu sortieren. Sortierung nach z.B. günstigster Gesamtpreis,
     * günstigster Stundenpreis, günstigster Kilometerpreis.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param art Art der Sortierung: gesamt | stunde | kilometer
     *
     * @return sortierte Liste von Dienstleistungen
     */
    @GetMapping("/sortiert/{art}")
    public List<Dienstleistung> getAllDienstleistungSorted(@CookieValue(name = "JSESSIONID") String cookieId,
                                              @PathVariable String art) {
        return dienstleistungService.getSorted(cookieId, art);
    }

    /**
     * Schnittstelle um eine Dienstleistung zu buchen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id von der gebuchten Dienstleistung
     * @return true, wenn Id vorhanden und Buchung erfolgreich, sonst exception
     */
    @GetMapping("/id/{id}/buchen")
    public boolean bookDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId,
                                              @PathVariable Long id) {
        return dienstleistungService.book(cookieId, id);
    }
}
