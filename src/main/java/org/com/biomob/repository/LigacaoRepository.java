package org.com.biomob.repository;

import org.com.biomob.domain.Ligacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ligacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigacaoRepository extends JpaRepository<Ligacao, Long> {}
