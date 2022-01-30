package com.newliving.backend.link.helfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface HelferRepository extends JpaRepository<Helfer, Long> {
}
