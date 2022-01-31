package com.newliving.backend.dienstleistung;

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
    public List<Dienstleistung> getAllDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId) {
        return dienstleistungService.getAll(cookieId);
    }

    /**
     * Schnittstelle um eine Dienstleistung anzuzeigen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id der Dienstleistung
     * @return Dienstleistung mit entsprechender Id
     */
    @GetMapping("/id")
    public Dienstleistung getDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId,
                                              @RequestParam Long id) {
        return dienstleistungService.getOne(cookieId, id);
    }

    /**
     * Schnittstelle um Dienstleistung nach Art gefiltert anzuzeigen. Filterung nach z.B. Anhänger oder Transporter
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param art      Art von Dienstleistung: anhänger | transporter
     * @return gefilterte Liste von Dienstleistungen
     */
    @GetMapping("/art")
    public List<Dienstleistung> getAllDienstleistungByType(@CookieValue(name = "JSESSIONID") String cookieId,
                                                           @RequestParam String art) {
        return dienstleistungService.getByType(cookieId, art);
    }

    /**
     * Schnittstelle um Dienstleistung nach Preisen zu sortieren. Sortierung nach z.B. günstigster Gesamtpreis,
     * günstigster Stundenpreis, günstigster Kilometerpreis.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param art      Art der Sortierung: gesamt | stunde | kilometer
     * @return sortierte Liste von Dienstleistungen
     */
    @GetMapping("/sortiert")
    public List<Dienstleistung> getAllDienstleistungSorted(@CookieValue(name = "JSESSIONID") String cookieId,
                                                           @RequestParam String art) {
        return dienstleistungService.getSorted(cookieId, art);
    }

    /**
     * Schnittstelle um eine Dienstleistung zu buchen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id       Id von der gebuchten Dienstleistung
     * @return true, wenn Id vorhanden und Buchung erfolgreich, sonst exception
     */
    @GetMapping("/buchen")
    public boolean bookDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId,
                                      @RequestParam Long id) {
        return dienstleistungService.book(cookieId, id);
    }
}
