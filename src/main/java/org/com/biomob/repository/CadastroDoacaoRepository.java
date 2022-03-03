package org.com.biomob.repository;

import org.com.biomob.domain.CadastroDoacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CadastroDoacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadastroDoacaoRepository extends JpaRepository<CadastroDoacao, Long> {}
