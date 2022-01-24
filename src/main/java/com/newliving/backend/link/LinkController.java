package com.newliving.backend.link;

import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.link.request.OfferHelpRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/link")
@AllArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/neu")
    public String createLink(@CookieValue(name = "JSESSIONID") String cookieId) {
        return linkService.create(cookieId);
    }

    @GetMapping("/deaktivieren")
    public boolean deactivateLink(@CookieValue(name = "JSESSIONID") String cookieId) {
        return linkService.deactivate(cookieId);
    }

    @GetMapping("/teilen")
    public boolean shareLink(@CookieValue(name = "JSESSIONID") String cookieId, List<String> emails) {
        return linkService.share(cookieId, emails);
    }

    @GetMapping("/id/{link_id}")
    public List<Eintrag> getAllEintragFriend(@PathVariable String link_id) {
        return linkService.getAllEintragFriend(link_id);
    }

    @PostMapping("/id/{link_id}/eintrag")
    public boolean offerHelpEintrag(@PathVariable String link_id, @RequestBody OfferHelpRequest request) {
        return linkService.offerHelpEintrag(link_id, request);
    }
}
