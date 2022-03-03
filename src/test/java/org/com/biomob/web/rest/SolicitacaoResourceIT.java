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
import org.com.biomob.domain.Solicitacao;
import org.com.biomob.repository.SolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SolicitacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SolicitacaoResourceIT {

    private static final String DEFAULT_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_SOLICITANTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANONIMA = false;
    private static final Boolean UPDATED_ANONIMA = true;

    private static final Instant DEFAULT_DATA_SOLICITACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_SOLICITACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ENDERECO_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO_ENTREGA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_DE_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_DE_ENTREGA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_APROVACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_APROVACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_APROVADO = false;
    private static final Boolean UPDATED_APROVADO = true;

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final String ENTITY_API_URL = "/api/solicitacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitacaoMockMvc;

    private Solicitacao solicitacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitacao createEntity(EntityManager em) {
        Solicitacao solicitacao = new Solicitacao()
            .solicitante(DEFAULT_SOLICITANTE)
            .anonima(DEFAULT_ANONIMA)
            .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
            .enderecoEntrega(DEFAULT_ENDERECO_ENTREGA)
            .localDeEntrega(DEFAULT_LOCAL_DE_ENTREGA)
            .dataAprovacao(DEFAULT_DATA_APROVACAO)
            .aprovado(DEFAULT_APROVADO)
            .ativa(DEFAULT_ATIVA);
        return solicitacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitacao createUpdatedEntity(EntityManager em) {
        Solicitacao solicitacao = new Solicitacao()
            .solicitante(UPDATED_SOLICITANTE)
            .anonima(UPDATED_ANONIMA)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .enderecoEntrega(UPDATED_ENDERECO_ENTREGA)
            .localDeEntrega(UPDATED_LOCAL_DE_ENTREGA)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .aprovado(UPDATED_APROVADO)
            .ativa(UPDATED_ATIVA);
        return solicitacao;
    }

    @BeforeEach
    public void initTest() {
        solicitacao = createEntity(em);
    }

    @Test
    @Transactional
    void createSolicitacao() throws Exception {
        int databaseSizeBeforeCreate = solicitacaoRepository.findAll().size();
        // Create the Solicitacao
        restSolicitacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacao)))
            .andExpect(status().isCreated());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitacao testSolicitacao = solicitacaoList.get(solicitacaoList.size() - 1);
        assertThat(testSolicitacao.getSolicitante()).isEqualTo(DEFAULT_SOLICITANTE);
        assertThat(testSolicitacao.getAnonima()).isEqualTo(DEFAULT_ANONIMA);
        assertThat(testSolicitacao.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testSolicitacao.getEnderecoEntrega()).isEqualTo(DEFAULT_ENDERECO_ENTREGA);
        assertThat(testSolicitacao.getLocalDeEntrega()).isEqualTo(DEFAULT_LOCAL_DE_ENTREGA);
        assertThat(testSolicitacao.getDataAprovacao()).isEqualTo(DEFAULT_DATA_APROVACAO);
        assertThat(testSolicitacao.getAprovado()).isEqualTo(DEFAULT_APROVADO);
        assertThat(testSolicitacao.getAtiva()).isEqualTo(DEFAULT_ATIVA);
    }

    @Test
    @Transactional
    void createSolicitacaoWithExistingId() throws Exception {
        // Create the Solicitacao with an existing ID
        solicitacao.setId(1L);

        int databaseSizeBeforeCreate = solicitacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacao)))
            .andExpect(status().isBadRequest());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSolicitacaos() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        // Get all the solicitacaoList
        restSolicitacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitante").value(hasItem(DEFAULT_SOLICITANTE)))
            .andExpect(jsonPath("$.[*].anonima").value(hasItem(DEFAULT_ANONIMA.booleanValue())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(DEFAULT_DATA_SOLICITACAO.toString())))
            .andExpect(jsonPath("$.[*].enderecoEntrega").value(hasItem(DEFAULT_ENDERECO_ENTREGA)))
            .andExpect(jsonPath("$.[*].localDeEntrega").value(hasItem(DEFAULT_LOCAL_DE_ENTREGA)))
            .andExpect(jsonPath("$.[*].dataAprovacao").value(hasItem(DEFAULT_DATA_APROVACAO.toString())))
            .andExpect(jsonPath("$.[*].aprovado").value(hasItem(DEFAULT_APROVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())));
    }

    @Test
    @Transactional
    void getSolicitacao() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        // Get the solicitacao
        restSolicitacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, solicitacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitacao.getId().intValue()))
            .andExpect(jsonPath("$.solicitante").value(DEFAULT_SOLICITANTE))
            .andExpect(jsonPath("$.anonima").value(DEFAULT_ANONIMA.booleanValue()))
            .andExpect(jsonPath("$.dataSolicitacao").value(DEFAULT_DATA_SOLICITACAO.toString()))
            .andExpect(jsonPath("$.enderecoEntrega").value(DEFAULT_ENDERECO_ENTREGA))
            .andExpect(jsonPath("$.localDeEntrega").value(DEFAULT_LOCAL_DE_ENTREGA))
            .andExpect(jsonPath("$.dataAprovacao").value(DEFAULT_DATA_APROVACAO.toString()))
            .andExpect(jsonPath("$.aprovado").value(DEFAULT_APROVADO.booleanValue()))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSolicitacao() throws Exception {
        // Get the solicitacao
        restSolicitacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSolicitacao() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();

        // Update the solicitacao
        Solicitacao updatedSolicitacao = solicitacaoRepository.findById(solicitacao.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitacao are not directly saved in db
        em.detach(updatedSolicitacao);
        updatedSolicitacao
            .solicitante(UPDATED_SOLICITANTE)
            .anonima(UPDATED_ANONIMA)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .enderecoEntrega(UPDATED_ENDERECO_ENTREGA)
            .localDeEntrega(UPDATED_LOCAL_DE_ENTREGA)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .aprovado(UPDATED_APROVADO)
            .ativa(UPDATED_ATIVA);

        restSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSolicitacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
        Solicitacao testSolicitacao = solicitacaoList.get(solicitacaoList.size() - 1);
        assertThat(testSolicitacao.getSolicitante()).isEqualTo(UPDATED_SOLICITANTE);
        assertThat(testSolicitacao.getAnonima()).isEqualTo(UPDATED_ANONIMA);
        assertThat(testSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testSolicitacao.getEnderecoEntrega()).isEqualTo(UPDATED_ENDERECO_ENTREGA);
        assertThat(testSolicitacao.getLocalDeEntrega()).isEqualTo(UPDATED_LOCAL_DE_ENTREGA);
        assertThat(testSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testSolicitacao.getAprovado()).isEqualTo(UPDATED_APROVADO);
        assertThat(testSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
    }

    @Test
    @Transactional
    void putNonExistingSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solicitacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(solicitacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSolicitacaoWithPatch() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();

        // Update the solicitacao using partial update
        Solicitacao partialUpdatedSolicitacao = new Solicitacao();
        partialUpdatedSolicitacao.setId(solicitacao.getId());

        partialUpdatedSolicitacao
            .anonima(UPDATED_ANONIMA)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .enderecoEntrega(UPDATED_ENDERECO_ENTREGA)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .aprovado(UPDATED_APROVADO)
            .ativa(UPDATED_ATIVA);

        restSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
        Solicitacao testSolicitacao = solicitacaoList.get(solicitacaoList.size() - 1);
        assertThat(testSolicitacao.getSolicitante()).isEqualTo(DEFAULT_SOLICITANTE);
        assertThat(testSolicitacao.getAnonima()).isEqualTo(UPDATED_ANONIMA);
        assertThat(testSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testSolicitacao.getEnderecoEntrega()).isEqualTo(UPDATED_ENDERECO_ENTREGA);
        assertThat(testSolicitacao.getLocalDeEntrega()).isEqualTo(DEFAULT_LOCAL_DE_ENTREGA);
        assertThat(testSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testSolicitacao.getAprovado()).isEqualTo(UPDATED_APROVADO);
        assertThat(testSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
    }

    @Test
    @Transactional
    void fullUpdateSolicitacaoWithPatch() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();

        // Update the solicitacao using partial update
        Solicitacao partialUpdatedSolicitacao = new Solicitacao();
        partialUpdatedSolicitacao.setId(solicitacao.getId());

        partialUpdatedSolicitacao
            .solicitante(UPDATED_SOLICITANTE)
            .anonima(UPDATED_ANONIMA)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .enderecoEntrega(UPDATED_ENDERECO_ENTREGA)
            .localDeEntrega(UPDATED_LOCAL_DE_ENTREGA)
            .dataAprovacao(UPDATED_DATA_APROVACAO)
            .aprovado(UPDATED_APROVADO)
            .ativa(UPDATED_ATIVA);

        restSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitacao))
            )
            .andExpect(status().isOk());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
        Solicitacao testSolicitacao = solicitacaoList.get(solicitacaoList.size() - 1);
        assertThat(testSolicitacao.getSolicitante()).isEqualTo(UPDATED_SOLICITANTE);
        assertThat(testSolicitacao.getAnonima()).isEqualTo(UPDATED_ANONIMA);
        assertThat(testSolicitacao.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testSolicitacao.getEnderecoEntrega()).isEqualTo(UPDATED_ENDERECO_ENTREGA);
        assertThat(testSolicitacao.getLocalDeEntrega()).isEqualTo(UPDATED_LOCAL_DE_ENTREGA);
        assertThat(testSolicitacao.getDataAprovacao()).isEqualTo(UPDATED_DATA_APROVACAO);
        assertThat(testSolicitacao.getAprovado()).isEqualTo(UPDATED_APROVADO);
        assertThat(testSolicitacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
    }

    @Test
    @Transactional
    void patchNonExistingSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, solicitacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSolicitacao() throws Exception {
        int databaseSizeBeforeUpdate = solicitacaoRepository.findAll().size();
        solicitacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(solicitacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solicitacao in the database
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSolicitacao() throws Exception {
        // Initialize the database
        solicitacaoRepository.saveAndFlush(solicitacao);

        int databaseSizeBeforeDelete = solicitacaoRepository.findAll().size();

        // Delete the solicitacao
        restSolicitacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, solicitacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Solicitacao> solicitacaoList = solicitacaoRepository.findAll();
        assertThat(solicitacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
