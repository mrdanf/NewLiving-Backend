package com.newliving.backend.dienstleistung;

import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.request.UpdatePasswortRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dienstleistung")
@AllArgsConstructor
public class DienstleistungController {

    private final DienstleistungService dienstleistungService;

    @GetMapping("")
    public List<Dienstleistung> getAllDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId) {
        return dienstleistungService.getAll(cookieId);
    }

    @GetMapping("/art/{art}")
    public List<Dienstleistung> getAllDienstleistungByType(@CookieValue(name = "JSESSIONID") String cookieId,
                                  @PathVariable String art) {
        return dienstleistungService.getByType(cookieId, art);
    }

    @GetMapping("/sortiert/{art}")
    public List<Dienstleistung> getAllDienstleistungSorted(@CookieValue(name = "JSESSIONID") String cookieId,
                                              @PathVariable String art) {
        return dienstleistungService.getSorted(cookieId, art);
    }

    @GetMapping("/id/{id}/buchen")
    public boolean bookDienstleistung(@CookieValue(name = "JSESSIONID") String cookieId,
                                              @PathVariable Long id) {
        return dienstleistungService.book(cookieId, id);
    }
}
