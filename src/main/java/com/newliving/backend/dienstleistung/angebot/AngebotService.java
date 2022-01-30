package com.newliving.backend.dienstleistung.angebot;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AngebotService {

    private final AngebotRepository angebotRepository;

    public List<Angebot> getAll() {
        return angebotRepository.findAll();
    }

}
