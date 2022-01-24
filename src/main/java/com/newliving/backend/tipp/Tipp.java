package com.newliving.backend.tipp;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipp")
@AllArgsConstructor
public class Tipp {

    private final TippService tippService;

    @GetMapping("")
    public List<Tipp> getAllTipp(@CookieValue(name = "JSESSIONID") String cookieId) {
        return tippService.getAll(cookieId);
    }

}
