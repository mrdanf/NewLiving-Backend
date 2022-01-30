package com.newliving.backend.dienstleistung.angebot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AngebotRepository extends JpaRepository<Angebot, Long> {

}
