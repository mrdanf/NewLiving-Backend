package com.newliving.backend.eintrag;

import com.newliving.backend.eintrag.request.CreateUpdateEintragRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eintrag")
@AllArgsConstructor
public class EintragController {

    private final EintragService eintragService;

    @GetMapping("")
    public List<Eintrag> getAllEintrag(@CookieValue(name = "JSESSIONID") String cookieId) {
        return eintragService.getAll(cookieId);
    }

    @GetMapping("/id/{id}")
    public Eintrag getEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @PathVariable Long id) {
        return eintragService.getOne(cookieId, id);
    }

    @PostMapping("/neu")
    public boolean createEintrag(@CookieValue(name = "JSESSIONID") String cookieId,
                                 @RequestBody CreateUpdateEintragRequest request) {
        return eintragService.create(cookieId, request);
    }

    @PutMapping("/id/{id}/erledigt")
    public boolean switchEintragErledigt(@CookieValue(name = "JSESSIONID") String cookieId, @PathVariable Long id) {
        return eintragService.switchErledigt(cookieId, id);
    }

    @PutMapping("/id/{id}/update")
    public boolean updateEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @PathVariable Long id,
                                 @RequestBody CreateUpdateEintragRequest request) {
        return eintragService.update(cookieId, id, request);
    }

    @DeleteMapping("/id/{id}/löschen")
    public boolean deleteEintrag(@CookieValue(name = "JSESSIONID") String cookieId, @PathVariable Long id) {
        return eintragService.delete(cookieId, id);
    }

}
