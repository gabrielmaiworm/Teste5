package org.com.biomob.repository;

import org.com.biomob.domain.Acao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Acao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {}
