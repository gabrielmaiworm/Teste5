package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CadastroDoacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CadastroDoacao.class);
        CadastroDoacao cadastroDoacao1 = new CadastroDoacao();
        cadastroDoacao1.setId(1L);
        CadastroDoacao cadastroDoacao2 = new CadastroDoacao();
        cadastroDoacao2.setId(cadastroDoacao1.getId());
        assertThat(cadastroDoacao1).isEqualTo(cadastroDoacao2);
        cadastroDoacao2.setId(2L);
        assertThat(cadastroDoacao1).isNotEqualTo(cadastroDoacao2);
        cadastroDoacao1.setId(null);
        assertThat(cadastroDoacao1).isNotEqualTo(cadastroDoacao2);
    }
}
