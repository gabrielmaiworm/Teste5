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
import org.com.biomob.domain.Ligacao;
import org.com.biomob.repository.LigacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LigacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LigacaoResourceIT {

    private static final String ENTITY_API_URL = "/api/ligacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LigacaoRepository ligacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigacaoMockMvc;

    private Ligacao ligacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligacao createEntity(EntityManager em) {
        Ligacao ligacao = new Ligacao();
        return ligacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ligacao createUpdatedEntity(EntityManager em) {
        Ligacao ligacao = new Ligacao();
        return ligacao;
    }

    @BeforeEach
    public void initTest() {
        ligacao = createEntity(em);
    }

    @Test
    @Transactional
    void createLigacao() throws Exception {
        int databaseSizeBeforeCreate = ligacaoRepository.findAll().size();
        // Create the Ligacao
        restLigacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligacao)))
            .andExpect(status().isCreated());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Ligacao testLigacao = ligacaoList.get(ligacaoList.size() - 1);
    }

    @Test
    @Transactional
    void createLigacaoWithExistingId() throws Exception {
        // Create the Ligacao with an existing ID
        ligacao.setId(1L);

        int databaseSizeBeforeCreate = ligacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligacao)))
            .andExpect(status().isBadRequest());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLigacaos() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        // Get all the ligacaoList
        restLigacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligacao.getId().intValue())));
    }

    @Test
    @Transactional
    void getLigacao() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        // Get the ligacao
        restLigacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, ligacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligacao.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLigacao() throws Exception {
        // Get the ligacao
        restLigacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLigacao() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();

        // Update the ligacao
        Ligacao updatedLigacao = ligacaoRepository.findById(ligacao.getId()).get();
        // Disconnect from session so that the updates on updatedLigacao are not directly saved in db
        em.detach(updatedLigacao);

        restLigacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLigacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLigacao))
            )
            .andExpect(status().isOk());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
        Ligacao testLigacao = ligacaoList.get(ligacaoList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ligacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ligacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ligacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLigacaoWithPatch() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();

        // Update the ligacao using partial update
        Ligacao partialUpdatedLigacao = new Ligacao();
        partialUpdatedLigacao.setId(ligacao.getId());

        restLigacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigacao))
            )
            .andExpect(status().isOk());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
        Ligacao testLigacao = ligacaoList.get(ligacaoList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateLigacaoWithPatch() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();

        // Update the ligacao using partial update
        Ligacao partialUpdatedLigacao = new Ligacao();
        partialUpdatedLigacao.setId(ligacao.getId());

        restLigacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLigacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLigacao))
            )
            .andExpect(status().isOk());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
        Ligacao testLigacao = ligacaoList.get(ligacaoList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ligacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ligacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLigacao() throws Exception {
        int databaseSizeBeforeUpdate = ligacaoRepository.findAll().size();
        ligacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLigacaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ligacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ligacao in the database
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLigacao() throws Exception {
        // Initialize the database
        ligacaoRepository.saveAndFlush(ligacao);

        int databaseSizeBeforeDelete = ligacaoRepository.findAll().size();

        // Delete the ligacao
        restLigacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ligacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ligacao> ligacaoList = ligacaoRepository.findAll();
        assertThat(ligacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
