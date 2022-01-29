package com.newliving.backend.link;

import com.newliving.backend.eintrag.Eintrag;
import com.newliving.backend.eintrag.EintragService;
import com.newliving.backend.email.EmailService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkService {

    private final NutzerService nutzerService;
    private final EintragService eintragService;
    private final EmailService emailService;

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
     * Versendet den Einladungslink an verschiedene Empfänger per Email.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param emails Liste von Emails, an welche die Einladung gesendet werden soll
     * @return true wenn eingeloggt, sonst exception
     */
    public boolean share(String cookieId, List<String> emails) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        String linkId = nutzer.getLink();

        String link = "http://localhost:8080/api/link/" + linkId;

        for (String to : emails) {
            emailService.send(to, emailService.buildEmailInvitation(nutzer.getName(), link));
        }

        return true;
    }


    private String createLink() {
        return RandomString.make(8);
    }

    public List<Eintrag> getAllEintragFriend(String link_id) {
        return eintragService.getAllFriend(link_id);
    }
}
