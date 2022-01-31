package com.newliving.backend.link.helfer;

import com.newliving.backend.eintrag.Eintrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface HelferRepository extends JpaRepository<Helfer, Long> {

    List<Helfer> findAllByEintrag(Eintrag eintrag);

}
