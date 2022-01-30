package com.newliving.backend.tipp;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Schnittstelle um Tipps zu lesen.
 */
@RestController
@RequestMapping("/api/tipp")
@AllArgsConstructor
public class TippController {

    private final TippService tippService;

    /**
     * Schnittstelle um alle Tipps zu holen.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von Tipps, wenn ein Nutzer eingeloggt ist, sonst null
     */
    @GetMapping("")
    public List<Tipp> getAllTipp(@CookieValue(name = "JSESSIONID") String cookieId) {
        return tippService.getAll(cookieId);
    }

}
