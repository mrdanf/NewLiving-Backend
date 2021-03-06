package com.newliving.backend.link;

import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.eintrag.EintragService;
import com.newliving.backend.email.EmailService;
import com.newliving.backend.link.helfer.Helfer;
import com.newliving.backend.link.helfer.HelferRepository;
import com.newliving.backend.link.request.GetListResponse;
import com.newliving.backend.link.request.OfferHelpRequest;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Verwaltet die Links, dazu gehört Erstellung, Teilen und Deaktivierung.
 */
@Service
@AllArgsConstructor
public class LinkService {

    private final NutzerService nutzerService;
    private final EintragService eintragService;
    private final EmailService emailService;
    private final HelferRepository helferRepository;

    /**
     * Erstellt einen zufälligen Link, der auf die Planung des Nutzers verweist.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Erstellter Link
     */
    public String create(String cookieId) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        String link = createLink();
        nutzer.setLink(link);
        nutzerService.save(nutzer);
        return link;
    }

    /**
     * Löscht den Link zur Planung.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return true wenn eingeloggt, sonst exception
     */
    public boolean deactivate(String cookieId) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        nutzer.setLink(null);
        nutzerService.save(nutzer);
        return true;
    }

    /**
     * Versendet den Freigabelink an einen Empfänger per Email.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param email    Email, an welche die Einladung gesendet werden soll
     * @return true wenn eingeloggt, sonst exception
     */
    public boolean share(String cookieId, String email) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        String linkId = nutzer.getLink();

        emailService.send(email, emailService.buildEmailInvitation(nutzer.getName(), linkId));

        return true;
    }

    private String createLink() {
        return RandomString.make(8);
    }

    /**
     * Holt die Planungsliste von dem Nutzer zugehörig zu dem Freigabelink. Fügt der Antwort noch den Namen des
     * Nutzers an.
     *
     * @param link_id Id vom Link, welcher auf die geteilte Planung verweist
     * @return Response: name, Liste von Einträgen
     */
    public GetListResponse getAllEintragFriend(String link_id) {
        String name = nutzerService.getNutzerByLink(link_id).getName();
        List<Eintrag> eintragList = eintragService.getAllFriend(link_id);
        GetListResponse response = new GetListResponse(name, eintragList);
        return response;
    }

    /**
     * Trägt bei einem Eintrag einen Helfer ein.
     *
     * @param link_id Id vom Link, welcher auf die geteilte Planung verweist
     * @param request Request: eintragId, name
     * @return true wenn Link und Eintrag existieren, sonst exception
     */
    public boolean offerHelpEintrag(String link_id, OfferHelpRequest request) {
        Eintrag eintrag = eintragService.getOneFriend(link_id, request.getEintragId());
        Helfer helfer = new Helfer(request.getName(), eintrag);
        helferRepository.save(helfer);
        return true;
    }
}
