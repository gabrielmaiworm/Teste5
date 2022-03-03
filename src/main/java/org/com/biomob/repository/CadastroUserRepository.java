package org.com.biomob.repository;

import org.com.biomob.domain.CadastroUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CadastroUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CadastroUserRepository extends JpaRepository<CadastroUser, Long> {}
