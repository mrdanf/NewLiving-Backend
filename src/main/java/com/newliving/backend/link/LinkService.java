package com.newliving.backend.link;

import com.newliving.backend.eintrag.EintragService;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkService {

    private final NutzerService nutzerService;
    private final EintragService eintragService;

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

    private String createLink() {
        return RandomString.make(8);
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
}
