package com.newliving.backend.nutzer;

import com.newliving.backend.nutzer.request.UpdateDatenRequest;
import com.newliving.backend.nutzer.request.UpdatePasswortRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class NutzerController {

    private final NutzerService nutzerService;

    @GetMapping("")
    public Nutzer getNutzer(@CookieValue(name = "JSESSIONID") String cookieId) {
        return nutzerService.getNutzerByCookie(cookieId);
    }

    @PutMapping("/update-passwort")
    public boolean updatePasswort(@CookieValue(name = "JSESSIONID") String cookieId,
                                  @RequestBody UpdatePasswortRequest request) {
        return nutzerService.updatePasswort(cookieId, request);
    }

    @PutMapping("/update-daten")
    public boolean updateDaten(@CookieValue(name = "JSESSIONID") String cookieId,
                              @RequestBody UpdateDatenRequest request) {
        return nutzerService.updateDaten(cookieId, request);
    }

}
