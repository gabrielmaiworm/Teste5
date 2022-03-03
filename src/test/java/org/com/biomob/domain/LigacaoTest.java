package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ligacao.class);
        Ligacao ligacao1 = new Ligacao();
        ligacao1.setId(1L);
        Ligacao ligacao2 = new Ligacao();
        ligacao2.setId(ligacao1.getId());
        assertThat(ligacao1).isEqualTo(ligacao2);
        ligacao2.setId(2L);
        assertThat(ligacao1).isNotEqualTo(ligacao2);
        ligacao1.setId(null);
        assertThat(ligacao1).isNotEqualTo(ligacao2);
    }
}
