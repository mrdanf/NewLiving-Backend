package com.newliving.backend.tipp;

import com.newliving.backend.nutzer.login.CheckLoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TippService {

    private final TippRepository tippRepository;
    private final CheckLoginService checkLoginService;

    /**
     * Liefert alle Tipps zurück, solange der Nutzer eingeloggt ist.
     *
     * @param cookieId Cookie vom eingeloggten Nutzer
     * @return Liste von Tipps, wenn ein Nutzer eingeloggt ist, sonst null
     */
    public List<Tipp> getAll(String cookieId) {
        if (checkLoginService.checkLoggedIn(cookieId)) {
            return tippRepository.findAll();
        }

        return null;
    }
}
