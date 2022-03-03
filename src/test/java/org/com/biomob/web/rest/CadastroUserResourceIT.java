package org.com.biomob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.com.biomob.IntegrationTest;
import org.com.biomob.domain.CadastroUser;
import org.com.biomob.repository.CadastroUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CadastroUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CadastroUserResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CEP = 1;
    private static final Integer UPDATED_CEP = 2;

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cadastro-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CadastroUserRepository cadastroUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCadastroUserMockMvc;

    private CadastroUser cadastroUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroUser createEntity(EntityManager em) {
        CadastroUser cadastroUser = new CadastroUser()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .tipo(DEFAULT_TIPO)
            .pais(DEFAULT_PAIS)
            .estado(DEFAULT_ESTADO)
            .cidade(DEFAULT_CIDADE)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO);
        return cadastroUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroUser createUpdatedEntity(EntityManager em) {
        CadastroUser cadastroUser = new CadastroUser()
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .tipo(UPDATED_TIPO)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO);
        return cadastroUser;
    }

    @BeforeEach
    public void initTest() {
        cadastroUser = createEntity(em);
    }

    @Test
    @Transactional
    void createCadastroUser() throws Exception {
        int databaseSizeBeforeCreate = cadastroUserRepository.findAll().size();
        // Create the CadastroUser
        restCadastroUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroUser)))
            .andExpect(status().isCreated());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeCreate + 1);
        CadastroUser testCadastroUser = cadastroUserList.get(cadastroUserList.size() - 1);
        assertThat(testCadastroUser.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCadastroUser.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testCadastroUser.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCadastroUser.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testCadastroUser.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCadastroUser.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCadastroUser.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCadastroUser.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCadastroUser.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testCadastroUser.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCadastroUser.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    void createCadastroUserWithExistingId() throws Exception {
        // Create the CadastroUser with an existing ID
        cadastroUser.setId(1L);

        int databaseSizeBeforeCreate = cadastroUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadastroUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroUser)))
            .andExpect(status().isBadRequest());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCadastroUsers() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        // Get all the cadastroUserList
        restCadastroUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)));
    }

    @Test
    @Transactional
    void getCadastroUser() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        // Get the cadastroUser
        restCadastroUserMockMvc
            .perform(get(ENTITY_API_URL_ID, cadastroUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cadastroUser.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO));
    }

    @Test
    @Transactional
    void getNonExistingCadastroUser() throws Exception {
        // Get the cadastroUser
        restCadastroUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCadastroUser() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();

        // Update the cadastroUser
        CadastroUser updatedCadastroUser = cadastroUserRepository.findById(cadastroUser.getId()).get();
        // Disconnect from session so that the updates on updatedCadastroUser are not directly saved in db
        em.detach(updatedCadastroUser);
        updatedCadastroUser
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .tipo(UPDATED_TIPO)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO);

        restCadastroUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCadastroUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCadastroUser))
            )
            .andExpect(status().isOk());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
        CadastroUser testCadastroUser = cadastroUserList.get(cadastroUserList.size() - 1);
        assertThat(testCadastroUser.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCadastroUser.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testCadastroUser.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCadastroUser.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCadastroUser.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCadastroUser.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroUser.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroUser.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCadastroUser.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCadastroUser.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroUser.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void putNonExistingCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cadastroUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCadastroUserWithPatch() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();

        // Update the cadastroUser using partial update
        CadastroUser partialUpdatedCadastroUser = new CadastroUser();
        partialUpdatedCadastroUser.setId(cadastroUser.getId());

        partialUpdatedCadastroUser.pais(UPDATED_PAIS).cidade(UPDATED_CIDADE).bairro(UPDATED_BAIRRO).numero(UPDATED_NUMERO);

        restCadastroUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroUser))
            )
            .andExpect(status().isOk());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
        CadastroUser testCadastroUser = cadastroUserList.get(cadastroUserList.size() - 1);
        assertThat(testCadastroUser.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCadastroUser.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testCadastroUser.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCadastroUser.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCadastroUser.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCadastroUser.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroUser.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroUser.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCadastroUser.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testCadastroUser.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroUser.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    void fullUpdateCadastroUserWithPatch() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();

        // Update the cadastroUser using partial update
        CadastroUser partialUpdatedCadastroUser = new CadastroUser();
        partialUpdatedCadastroUser.setId(cadastroUser.getId());

        partialUpdatedCadastroUser
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .tipo(UPDATED_TIPO)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .cidade(UPDATED_CIDADE)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO);

        restCadastroUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroUser))
            )
            .andExpect(status().isOk());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
        CadastroUser testCadastroUser = cadastroUserList.get(cadastroUserList.size() - 1);
        assertThat(testCadastroUser.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCadastroUser.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testCadastroUser.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCadastroUser.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCadastroUser.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCadastroUser.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroUser.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroUser.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCadastroUser.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCadastroUser.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroUser.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cadastroUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCadastroUser() throws Exception {
        int databaseSizeBeforeUpdate = cadastroUserRepository.findAll().size();
        cadastroUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cadastroUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroUser in the database
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCadastroUser() throws Exception {
        // Initialize the database
        cadastroUserRepository.saveAndFlush(cadastroUser);

        int databaseSizeBeforeDelete = cadastroUserRepository.findAll().size();

        // Delete the cadastroUser
        restCadastroUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, cadastroUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CadastroUser> cadastroUserList = cadastroUserRepository.findAll();
        assertThat(cadastroUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
