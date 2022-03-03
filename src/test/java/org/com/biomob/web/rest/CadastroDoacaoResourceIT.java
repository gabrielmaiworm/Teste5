package org.com.biomob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.com.biomob.IntegrationTest;
import org.com.biomob.domain.CadastroDoacao;
import org.com.biomob.repository.CadastroDoacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CadastroDoacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CadastroDoacaoResourceIT {

    private static final Boolean DEFAULT_DOACAO_ANONIMA = false;
    private static final Boolean UPDATED_DOACAO_ANONIMA = true;

    private static final Boolean DEFAULT_REALIZA_ENTREGA = false;
    private static final Boolean UPDATED_REALIZA_ENTREGA = true;

    private static final Instant DEFAULT_DATA_DOACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_DOACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cadastro-doacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CadastroDoacaoRepository cadastroDoacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCadastroDoacaoMockMvc;

    private CadastroDoacao cadastroDoacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroDoacao createEntity(EntityManager em) {
        CadastroDoacao cadastroDoacao = new CadastroDoacao()
            .doacaoAnonima(DEFAULT_DOACAO_ANONIMA)
            .realizaEntrega(DEFAULT_REALIZA_ENTREGA)
            .dataDoacao(DEFAULT_DATA_DOACAO)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .cep(DEFAULT_CEP)
            .estado(DEFAULT_ESTADO)
            .pais(DEFAULT_PAIS)
            .complemento(DEFAULT_COMPLEMENTO);
        return cadastroDoacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CadastroDoacao createUpdatedEntity(EntityManager em) {
        CadastroDoacao cadastroDoacao = new CadastroDoacao()
            .doacaoAnonima(UPDATED_DOACAO_ANONIMA)
            .realizaEntrega(UPDATED_REALIZA_ENTREGA)
            .dataDoacao(UPDATED_DATA_DOACAO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .cep(UPDATED_CEP)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .complemento(UPDATED_COMPLEMENTO);
        return cadastroDoacao;
    }

    @BeforeEach
    public void initTest() {
        cadastroDoacao = createEntity(em);
    }

    @Test
    @Transactional
    void createCadastroDoacao() throws Exception {
        int databaseSizeBeforeCreate = cadastroDoacaoRepository.findAll().size();
        // Create the CadastroDoacao
        restCadastroDoacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isCreated());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeCreate + 1);
        CadastroDoacao testCadastroDoacao = cadastroDoacaoList.get(cadastroDoacaoList.size() - 1);
        assertThat(testCadastroDoacao.getDoacaoAnonima()).isEqualTo(DEFAULT_DOACAO_ANONIMA);
        assertThat(testCadastroDoacao.getRealizaEntrega()).isEqualTo(DEFAULT_REALIZA_ENTREGA);
        assertThat(testCadastroDoacao.getDataDoacao()).isEqualTo(DEFAULT_DATA_DOACAO);
        assertThat(testCadastroDoacao.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testCadastroDoacao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCadastroDoacao.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCadastroDoacao.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCadastroDoacao.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCadastroDoacao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCadastroDoacao.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testCadastroDoacao.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    void createCadastroDoacaoWithExistingId() throws Exception {
        // Create the CadastroDoacao with an existing ID
        cadastroDoacao.setId(1L);

        int databaseSizeBeforeCreate = cadastroDoacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadastroDoacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCadastroDoacaos() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        // Get all the cadastroDoacaoList
        restCadastroDoacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroDoacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].doacaoAnonima").value(hasItem(DEFAULT_DOACAO_ANONIMA.booleanValue())))
            .andExpect(jsonPath("$.[*].realizaEntrega").value(hasItem(DEFAULT_REALIZA_ENTREGA.booleanValue())))
            .andExpect(jsonPath("$.[*].dataDoacao").value(hasItem(DEFAULT_DATA_DOACAO.toString())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)));
    }

    @Test
    @Transactional
    void getCadastroDoacao() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        // Get the cadastroDoacao
        restCadastroDoacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, cadastroDoacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cadastroDoacao.getId().intValue()))
            .andExpect(jsonPath("$.doacaoAnonima").value(DEFAULT_DOACAO_ANONIMA.booleanValue()))
            .andExpect(jsonPath("$.realizaEntrega").value(DEFAULT_REALIZA_ENTREGA.booleanValue()))
            .andExpect(jsonPath("$.dataDoacao").value(DEFAULT_DATA_DOACAO.toString()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO));
    }

    @Test
    @Transactional
    void getNonExistingCadastroDoacao() throws Exception {
        // Get the cadastroDoacao
        restCadastroDoacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCadastroDoacao() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();

        // Update the cadastroDoacao
        CadastroDoacao updatedCadastroDoacao = cadastroDoacaoRepository.findById(cadastroDoacao.getId()).get();
        // Disconnect from session so that the updates on updatedCadastroDoacao are not directly saved in db
        em.detach(updatedCadastroDoacao);
        updatedCadastroDoacao
            .doacaoAnonima(UPDATED_DOACAO_ANONIMA)
            .realizaEntrega(UPDATED_REALIZA_ENTREGA)
            .dataDoacao(UPDATED_DATA_DOACAO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .cep(UPDATED_CEP)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .complemento(UPDATED_COMPLEMENTO);

        restCadastroDoacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCadastroDoacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCadastroDoacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroDoacao testCadastroDoacao = cadastroDoacaoList.get(cadastroDoacaoList.size() - 1);
        assertThat(testCadastroDoacao.getDoacaoAnonima()).isEqualTo(UPDATED_DOACAO_ANONIMA);
        assertThat(testCadastroDoacao.getRealizaEntrega()).isEqualTo(UPDATED_REALIZA_ENTREGA);
        assertThat(testCadastroDoacao.getDataDoacao()).isEqualTo(UPDATED_DATA_DOACAO);
        assertThat(testCadastroDoacao.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCadastroDoacao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroDoacao.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroDoacao.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroDoacao.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCadastroDoacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCadastroDoacao.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCadastroDoacao.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void putNonExistingCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cadastroDoacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cadastroDoacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCadastroDoacaoWithPatch() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();

        // Update the cadastroDoacao using partial update
        CadastroDoacao partialUpdatedCadastroDoacao = new CadastroDoacao();
        partialUpdatedCadastroDoacao.setId(cadastroDoacao.getId());

        partialUpdatedCadastroDoacao.dataDoacao(UPDATED_DATA_DOACAO).logradouro(UPDATED_LOGRADOURO).cidade(UPDATED_CIDADE);

        restCadastroDoacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroDoacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroDoacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroDoacao testCadastroDoacao = cadastroDoacaoList.get(cadastroDoacaoList.size() - 1);
        assertThat(testCadastroDoacao.getDoacaoAnonima()).isEqualTo(DEFAULT_DOACAO_ANONIMA);
        assertThat(testCadastroDoacao.getRealizaEntrega()).isEqualTo(DEFAULT_REALIZA_ENTREGA);
        assertThat(testCadastroDoacao.getDataDoacao()).isEqualTo(UPDATED_DATA_DOACAO);
        assertThat(testCadastroDoacao.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCadastroDoacao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCadastroDoacao.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCadastroDoacao.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroDoacao.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCadastroDoacao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCadastroDoacao.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testCadastroDoacao.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    void fullUpdateCadastroDoacaoWithPatch() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();

        // Update the cadastroDoacao using partial update
        CadastroDoacao partialUpdatedCadastroDoacao = new CadastroDoacao();
        partialUpdatedCadastroDoacao.setId(cadastroDoacao.getId());

        partialUpdatedCadastroDoacao
            .doacaoAnonima(UPDATED_DOACAO_ANONIMA)
            .realizaEntrega(UPDATED_REALIZA_ENTREGA)
            .dataDoacao(UPDATED_DATA_DOACAO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .cep(UPDATED_CEP)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .complemento(UPDATED_COMPLEMENTO);

        restCadastroDoacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCadastroDoacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCadastroDoacao))
            )
            .andExpect(status().isOk());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
        CadastroDoacao testCadastroDoacao = cadastroDoacaoList.get(cadastroDoacaoList.size() - 1);
        assertThat(testCadastroDoacao.getDoacaoAnonima()).isEqualTo(UPDATED_DOACAO_ANONIMA);
        assertThat(testCadastroDoacao.getRealizaEntrega()).isEqualTo(UPDATED_REALIZA_ENTREGA);
        assertThat(testCadastroDoacao.getDataDoacao()).isEqualTo(UPDATED_DATA_DOACAO);
        assertThat(testCadastroDoacao.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCadastroDoacao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroDoacao.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroDoacao.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroDoacao.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCadastroDoacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCadastroDoacao.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCadastroDoacao.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cadastroDoacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCadastroDoacao() throws Exception {
        int databaseSizeBeforeUpdate = cadastroDoacaoRepository.findAll().size();
        cadastroDoacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCadastroDoacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cadastroDoacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CadastroDoacao in the database
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCadastroDoacao() throws Exception {
        // Initialize the database
        cadastroDoacaoRepository.saveAndFlush(cadastroDoacao);

        int databaseSizeBeforeDelete = cadastroDoacaoRepository.findAll().size();

        // Delete the cadastroDoacao
        restCadastroDoacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cadastroDoacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CadastroDoacao> cadastroDoacaoList = cadastroDoacaoRepository.findAll();
        assertThat(cadastroDoacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
