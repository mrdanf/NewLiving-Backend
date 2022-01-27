package com.newliving.backend.eintrag;

import com.newliving.backend.nutzer.Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface EintragRepository extends JpaRepository<Eintrag, Long> {

    List<Eintrag> findAllByNutzer(Nutzer nutzer);

    Optional<Eintrag> findEintragByIdAndNutzer(Long id, Nutzer nutzer);

}
