package com.newliving.backend.registrierung;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrierung")
@AllArgsConstructor
public class RegistrierungController {

    private final RegistrierungService registrierungService;

    @PostMapping("")
    public boolean register(@RequestBody RegistrierungRequest request) {
        return registrierungService.register(request);
    }

    @GetMapping("/passwort-vergessen")
    public boolean resetPasswort(@RequestParam String email) {
        return registrierungService.resetPasswort(email);
    }
    
}
