package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acao.class);
        Acao acao1 = new Acao();
        acao1.setId(1L);
        Acao acao2 = new Acao();
        acao2.setId(acao1.getId());
        assertThat(acao1).isEqualTo(acao2);
        acao2.setId(2L);
        assertThat(acao1).isNotEqualTo(acao2);
        acao1.setId(null);
        assertThat(acao1).isNotEqualTo(acao2);
    }
}
