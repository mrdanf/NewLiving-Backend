package com.newliving.backend.nutzer.registrierung;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrierung")
@AllArgsConstructor
public class RegistrierungController {

    private final RegistrierungService registrierungService;

    @PostMapping("")
    public boolean register(@RequestBody RegistrierungRequest request) {
        String token = registrierungService.register(request);

        return token != null;
    }

    @GetMapping("/passwort-vergessen")
    public boolean resetPasswort(@RequestParam String email) {
        return registrierungService.resetPasswort(email);
    }
    
}
