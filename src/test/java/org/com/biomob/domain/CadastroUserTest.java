package org.com.biomob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.com.biomob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CadastroUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CadastroUser.class);
        CadastroUser cadastroUser1 = new CadastroUser();
        cadastroUser1.setId(1L);
        CadastroUser cadastroUser2 = new CadastroUser();
        cadastroUser2.setId(cadastroUser1.getId());
        assertThat(cadastroUser1).isEqualTo(cadastroUser2);
        cadastroUser2.setId(2L);
        assertThat(cadastroUser1).isNotEqualTo(cadastroUser2);
        cadastroUser1.setId(null);
        assertThat(cadastroUser1).isNotEqualTo(cadastroUser2);
    }
}
