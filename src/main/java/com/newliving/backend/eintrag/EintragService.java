package com.newliving.backend.eintrag;

import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
