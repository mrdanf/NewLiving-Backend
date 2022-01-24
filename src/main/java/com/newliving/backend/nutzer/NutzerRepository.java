package com.newliving.backend.nutzer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface NutzerRepository extends JpaRepository<Nutzer, Long> {

    Optional<Nutzer> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Nutzer n SET n.passwort = ?2 WHERE n.id = ?1")
    void updatePasswortById(Long id, String passwort);
}
