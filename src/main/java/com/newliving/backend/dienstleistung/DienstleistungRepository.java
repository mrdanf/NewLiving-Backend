package com.newliving.backend.dienstleistung;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DienstleistungRepository extends JpaRepository<Dienstleistung, Long> {

    List<Dienstleistung> findAllByTypContaining(String typ);
}
