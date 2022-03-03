package org.com.biomob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.com.biomob.IntegrationTest;
import org.com.biomob.domain.Acao;
import org.com.biomob.repository.AcaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AcaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcaoResourceIT {

    private static final Instant DEFAULT_DATA_ACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO_CRIACAO_ACAO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_CRIACAO_ACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PENDENTE = false;
    private static final Boolean UPDATED_PENDENTE = true;

    private static final LocalDate DEFAULT_DATA_EXECUCAO_ACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EXECUCAO_ACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/acaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcaoMockMvc;

    private Acao acao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acao createEntity(EntityManager em) {
        Acao acao = new Acao()
            .dataAcao(DEFAULT_DATA_ACAO)
            .usuarioCriacaoAcao(DEFAULT_USUARIO_CRIACAO_ACAO)
            .pendente(DEFAULT_PENDENTE)
            .dataExecucaoAcao(DEFAULT_DATA_EXECUCAO_ACAO)
            .ativa(DEFAULT_ATIVA)
            .observacoes(DEFAULT_OBSERVACOES);
        return acao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acao createUpdatedEntity(EntityManager em) {
        Acao acao = new Acao()
            .dataAcao(UPDATED_DATA_ACAO)
            .usuarioCriacaoAcao(UPDATED_USUARIO_CRIACAO_ACAO)
            .pendente(UPDATED_PENDENTE)
            .dataExecucaoAcao(UPDATED_DATA_EXECUCAO_ACAO)
            .ativa(UPDATED_ATIVA)
            .observacoes(UPDATED_OBSERVACOES);
        return acao;
    }

    @BeforeEach
    public void initTest() {
        acao = createEntity(em);
    }

    @Test
    @Transactional
    void createAcao() throws Exception {
        int databaseSizeBeforeCreate = acaoRepository.findAll().size();
        // Create the Acao
        restAcaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isCreated());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeCreate + 1);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getDataAcao()).isEqualTo(DEFAULT_DATA_ACAO);
        assertThat(testAcao.getUsuarioCriacaoAcao()).isEqualTo(DEFAULT_USUARIO_CRIACAO_ACAO);
        assertThat(testAcao.getPendente()).isEqualTo(DEFAULT_PENDENTE);
        assertThat(testAcao.getDataExecucaoAcao()).isEqualTo(DEFAULT_DATA_EXECUCAO_ACAO);
        assertThat(testAcao.getAtiva()).isEqualTo(DEFAULT_ATIVA);
        assertThat(testAcao.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    void createAcaoWithExistingId() throws Exception {
        // Create the Acao with an existing ID
        acao.setId(1L);

        int databaseSizeBeforeCreate = acaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcaos() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        // Get all the acaoList
        restAcaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAcao").value(hasItem(DEFAULT_DATA_ACAO.toString())))
            .andExpect(jsonPath("$.[*].usuarioCriacaoAcao").value(hasItem(DEFAULT_USUARIO_CRIACAO_ACAO)))
            .andExpect(jsonPath("$.[*].pendente").value(hasItem(DEFAULT_PENDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataExecucaoAcao").value(hasItem(DEFAULT_DATA_EXECUCAO_ACAO.toString())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)));
    }

    @Test
    @Transactional
    void getAcao() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        // Get the acao
        restAcaoMockMvc
            .perform(get(ENTITY_API_URL_ID, acao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acao.getId().intValue()))
            .andExpect(jsonPath("$.dataAcao").value(DEFAULT_DATA_ACAO.toString()))
            .andExpect(jsonPath("$.usuarioCriacaoAcao").value(DEFAULT_USUARIO_CRIACAO_ACAO))
            .andExpect(jsonPath("$.pendente").value(DEFAULT_PENDENTE.booleanValue()))
            .andExpect(jsonPath("$.dataExecucaoAcao").value(DEFAULT_DATA_EXECUCAO_ACAO.toString()))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES));
    }

    @Test
    @Transactional
    void getNonExistingAcao() throws Exception {
        // Get the acao
        restAcaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcao() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();

        // Update the acao
        Acao updatedAcao = acaoRepository.findById(acao.getId()).get();
        // Disconnect from session so that the updates on updatedAcao are not directly saved in db
        em.detach(updatedAcao);
        updatedAcao
            .dataAcao(UPDATED_DATA_ACAO)
            .usuarioCriacaoAcao(UPDATED_USUARIO_CRIACAO_ACAO)
            .pendente(UPDATED_PENDENTE)
            .dataExecucaoAcao(UPDATED_DATA_EXECUCAO_ACAO)
            .ativa(UPDATED_ATIVA)
            .observacoes(UPDATED_OBSERVACOES);

        restAcaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcao))
            )
            .andExpect(status().isOk());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getDataAcao()).isEqualTo(UPDATED_DATA_ACAO);
        assertThat(testAcao.getUsuarioCriacaoAcao()).isEqualTo(UPDATED_USUARIO_CRIACAO_ACAO);
        assertThat(testAcao.getPendente()).isEqualTo(UPDATED_PENDENTE);
        assertThat(testAcao.getDataExecucaoAcao()).isEqualTo(UPDATED_DATA_EXECUCAO_ACAO);
        assertThat(testAcao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testAcao.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void putNonExistingAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcaoWithPatch() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();

        // Update the acao using partial update
        Acao partialUpdatedAcao = new Acao();
        partialUpdatedAcao.setId(acao.getId());

        partialUpdatedAcao.dataAcao(UPDATED_DATA_ACAO).ativa(UPDATED_ATIVA).observacoes(UPDATED_OBSERVACOES);

        restAcaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcao))
            )
            .andExpect(status().isOk());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getDataAcao()).isEqualTo(UPDATED_DATA_ACAO);
        assertThat(testAcao.getUsuarioCriacaoAcao()).isEqualTo(DEFAULT_USUARIO_CRIACAO_ACAO);
        assertThat(testAcao.getPendente()).isEqualTo(DEFAULT_PENDENTE);
        assertThat(testAcao.getDataExecucaoAcao()).isEqualTo(DEFAULT_DATA_EXECUCAO_ACAO);
        assertThat(testAcao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testAcao.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void fullUpdateAcaoWithPatch() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();

        // Update the acao using partial update
        Acao partialUpdatedAcao = new Acao();
        partialUpdatedAcao.setId(acao.getId());

        partialUpdatedAcao
            .dataAcao(UPDATED_DATA_ACAO)
            .usuarioCriacaoAcao(UPDATED_USUARIO_CRIACAO_ACAO)
            .pendente(UPDATED_PENDENTE)
            .dataExecucaoAcao(UPDATED_DATA_EXECUCAO_ACAO)
            .ativa(UPDATED_ATIVA)
            .observacoes(UPDATED_OBSERVACOES);

        restAcaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcao))
            )
            .andExpect(status().isOk());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
        Acao testAcao = acaoList.get(acaoList.size() - 1);
        assertThat(testAcao.getDataAcao()).isEqualTo(UPDATED_DATA_ACAO);
        assertThat(testAcao.getUsuarioCriacaoAcao()).isEqualTo(UPDATED_USUARIO_CRIACAO_ACAO);
        assertThat(testAcao.getPendente()).isEqualTo(UPDATED_PENDENTE);
        assertThat(testAcao.getDataExecucaoAcao()).isEqualTo(UPDATED_DATA_EXECUCAO_ACAO);
        assertThat(testAcao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testAcao.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void patchNonExistingAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcao() throws Exception {
        int databaseSizeBeforeUpdate = acaoRepository.findAll().size();
        acao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(acao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acao in the database
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcao() throws Exception {
        // Initialize the database
        acaoRepository.saveAndFlush(acao);

        int databaseSizeBeforeDelete = acaoRepository.findAll().size();

        // Delete the acao
        restAcaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, acao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acao> acaoList = acaoRepository.findAll();
        assertThat(acaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
