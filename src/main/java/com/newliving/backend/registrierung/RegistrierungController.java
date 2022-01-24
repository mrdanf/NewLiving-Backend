package com.newliving.backend.registrierung;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Schnittstellen für Registrierung und Passwort zurücksetzen.
 */
@RestController
@RequestMapping("/api/registrierung")
@AllArgsConstructor
public class RegistrierungController {

    private final RegistrierungService registrierungService;

    /**
     * Schnittstelle zur Erstellung eines neuen Accounts.
     *
     * @param request Request mit Registrierungsdaten: Email, Name, Passwort. Rest (Adresse und IBAN) ist optional
     * @return true, wenn die Registrierung geklappt hat, ansonsten false
     */
    @PostMapping("")
    public boolean register(@RequestBody RegistrierungRequest request) {
        return registrierungService.register(request);
    }

    /**
     * Schnittstelle zum Zurücksetzen des Passworts.
     *
     * @param email Email von dem dazugehörigen Account
     * @return true, wenn die Email existiert und das Zurücksetzen geklappt hat, ansonsten exception
     */
    @GetMapping("/passwort-vergessen")
    public boolean resetPasswort(@RequestParam String email) {
        return registrierungService.resetPasswort(email);
    }
    
}
