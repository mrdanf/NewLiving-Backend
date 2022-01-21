package com.newliving.backend.nutzer.login;

import com.newliving.backend.nutzer.login.request.LoginDatenRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public boolean login(HttpServletResponse httpServletResponse, @RequestBody LoginDatenRequest request) {
        return loginService.login(httpServletResponse, request);
    }

    @RequestMapping("/ausloggen")
    public boolean logout(@CookieValue(name = "JSESSIONID") String cookieId) {
        return loginService.logout(cookieId);
    }

}
