package com.newliving.backend.nutzer.login;

import com.newliving.backend.nutzer.Nutzer;
import com.newliving.backend.nutzer.NutzerService;
import com.newliving.backend.nutzer.login.request.LoginDatenRequest;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class LoginService {

    private final NutzerService nutzerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean login(HttpServletResponse httpServletResponse, LoginDatenRequest request) {
        String email = request.getEmail();
        String passwort = request.getPasswort();

        Nutzer nutzer = nutzerService.getNutzer(email);
        if (bCryptPasswordEncoder.matches(passwort, nutzer.getPasswort())) {

            String cookie = createCookieId(email);
            nutzerService.setCookie(nutzer.getId(), cookie);
            httpServletResponse.addCookie(new Cookie("JSESSIONID", cookie));

            return true;
        } else {
            throw new IllegalStateException("Passwort falsch.");
        }
    }

    private String createCookieId(String email) {
        StringBuilder result = new StringBuilder(email);
        result.append(RandomString.make());

        return result.toString();
    }

    public boolean logout(String cookieId) {
        return nutzerService.deleteCookie(cookieId);
    }
}
