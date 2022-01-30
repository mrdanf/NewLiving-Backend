package com.newliving.backend.eintrag;

import com.newliving.backend.eintrag.request.CreateOrUpdateEintragRequest;
import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Zuständig für das Holen der Einträge, Erstellen von Einträgen, Aktualisieren der Informationen sowie den Status
 * von Erledigt, und das Löschen.
 */
@Service
@AllArgsConstructor
public class EintragService {

    private final EintragRepository eintragRepository;
    private final NutzerService nutzerService;

    /**
     * Holt alle Einträge, die zum eingeloggten Nutzer gehören.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von Einträgen wenn eingeloggt, sonst exception
     */
    public List<Eintrag> getAll(String cookieId) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        return eintragRepository.findAllByNutzer(nutzer);
    }

    /**
     * Holt alle Einträge, die dem Nutzer gehören, zu wem der Link gehört.
     *
     * @param link_id Link vom zugehörigen Nutzer
     * @return Liste von Einträgen wenn link_id korrekt, sonst exception
     */
    public List<Eintrag> getAllFriend(String link_id) {
        Nutzer nutzer = nutzerService.getNutzerByLink(link_id);
        return eintragRepository.findAllByNutzer(nutzer);
    }

    /**
     * Holt einen einzelnen Eintrag.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return Einen Eintrag, wenn der Nutzer eingeloggt ist und der Eintrag ihm gehört, sonst exception
     */
    public Eintrag getOne(String cookieId, Long id) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        return eintragRepository.findEintragByIdAndNutzer(id, nutzer).get();
    }

    /**
     * Holt einen Eintrag, der dem Nutzer gehört, zu wem der Link gehört.
     *
     * @param link_id Link vom zugehörigen Nutzer
     * @return Eintrag wenn link_id korrekt, sonst exception
     */
    public Eintrag getOneFriend(String link_id, Long eintragId) {
        Nutzer nutzer = nutzerService.getNutzerByLink(link_id);
        return eintragRepository.findByNutzerAndId(nutzer, eintragId).get();
    }

    /**
     * Erstellt einen Eintrag und weist ihn der Liste des eingeloggten Nutzers zu.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param request Request: text, datum
     * @return true wenn eingeloggt, sonst exception
     */
    public boolean create(String cookieId, CreateOrUpdateEintragRequest request) {
        Nutzer nutzer = nutzerService.getNutzerByCookie(cookieId);
        Eintrag eintrag = new Eintrag(request.getText(), request.getDatum(), nutzer);
        eintragRepository.save(eintrag);
        return true;
    }

    /**
     * Prüft erst nach Authentifizierung und setzt den durch Id identifizierten Eintrag als erledigt oder doch nicht
     * erledigt.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return true wenn authentifiziert und Eintrag vorhanden, sonst false
     */
    public boolean switchErledigt(String cookieId, Long id) {
        Optional<Eintrag> eintragOptional = eintragRepository.findById(id);
        if (eintragExistsAndAuthenticated(cookieId, eintragOptional)) {
            Eintrag eintrag = eintragOptional.get();
            eintrag.switchErledigt();

            eintragRepository.save(eintrag);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prüft erst nach Authentifizierung und aktualisiert die Daten (Text und Datum) eines Eintrags.
     *
     * @param cookieId cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @param request Request: text, datum
     * @return true wenn authentifiziert und Eintrag vorhanden, sonst false
     */
    public boolean update(String cookieId, Long id, CreateOrUpdateEintragRequest request) {
        Optional<Eintrag> eintragOptional = eintragRepository.findById(id);
        if (eintragExistsAndAuthenticated(cookieId, eintragOptional)) {
            Eintrag eintrag = eintragOptional.get();

            if (!request.getText().equals("")) {
                eintrag.setText(request.getText());
            }
            if (!request.getDatum().equals("")) {
                eintrag.setDatum(request.getDatum());
            }

            eintragRepository.save(eintrag);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prüft erst nach Authentifizierung und löscht einen Eintrag.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param id Id des Eintrags
     * @return true wenn authentifiziert und Eintrag vorhanden, sonst false
     */
    public boolean delete(String cookieId, Long id) {
        Optional<Eintrag> eintragOptional = eintragRepository.findById(id);
        if (eintragExistsAndAuthenticated(cookieId, eintragOptional)) {
            eintragRepository.delete(eintragOptional.get());

            return true;
        } else {
            return false;
        }
    }

    /**
     * Überprüft, ob der Eintrag existiert und ob der Nutzer die Berechtigung hat, den Eintrag zu bearbeiten.
     *
     * Damit der Eintrag nicht von unbefugten Personen überarbeitet werden kann, wird eine Authentifizierung
     * durchgeführt. Zuerst wird überprüft, ob der Eintrag existiert. Danach, ob die Person eingeloggt ist
     * und ob der Eintrag auch überhaupt zum eingeloggten Nutzer gehört.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @param eintrag Eintrag der bearbeitet werden soll
     * @return true wenn eingeloggt und Eintrag vorhanden und dem Nutzer gehört, sonst false
     */
    private boolean eintragExistsAndAuthenticated(String cookieId, Optional<Eintrag> eintrag) {
        if (eintrag.isEmpty()) {
            // Eintrag mit der Id existiert nicht
            return false;
        }

        // Authentifizierung
        String nutzerCookieId = eintrag.get().getNutzer().getCookieId();
        if (cookieId.equals(nutzerCookieId)) {
            return true;
        } else {
            return false;
        }
    }

}
