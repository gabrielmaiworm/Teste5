package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.com.biomob.domain.Acao;
import org.com.biomob.repository.AcaoRepository;
import org.com.biomob.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.com.biomob.domain.Acao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AcaoResource {

    private final Logger log = LoggerFactory.getLogger(AcaoResource.class);

    private static final String ENTITY_NAME = "acao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcaoRepository acaoRepository;

    public AcaoResource(AcaoRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
    }

    /**
     * {@code POST  /acaos} : Create a new acao.
     *
     * @param acao the acao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acao, or with status {@code 400 (Bad Request)} if the acao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acaos")
    public ResponseEntity<Acao> createAcao(@RequestBody Acao acao) throws URISyntaxException {
        log.debug("REST request to save Acao : {}", acao);
        if (acao.getId() != null) {
            throw new BadRequestAlertException("A new acao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acao result = acaoRepository.save(acao);
        return ResponseEntity
            .created(new URI("/api/acaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acaos/:id} : Updates an existing acao.
     *
     * @param id the id of the acao to save.
     * @param acao the acao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acao,
     * or with status {@code 400 (Bad Request)} if the acao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acaos/{id}")
    public ResponseEntity<Acao> updateAcao(@PathVariable(value = "id", required = false) final Long id, @RequestBody Acao acao)
        throws URISyntaxException {
        log.debug("REST request to update Acao : {}, {}", id, acao);
        if (acao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Acao result = acaoRepository.save(acao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acaos/:id} : Partial updates given fields of an existing acao, field will ignore if it is null
     *
     * @param id the id of the acao to save.
     * @param acao the acao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acao,
     * or with status {@code 400 (Bad Request)} if the acao is not valid,
     * or with status {@code 404 (Not Found)} if the acao is not found,
     * or with status {@code 500 (Internal Server Error)} if the acao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Acao> partialUpdateAcao(@PathVariable(value = "id", required = false) final Long id, @RequestBody Acao acao)
        throws URISyntaxException {
        log.debug("REST request to partial update Acao partially : {}, {}", id, acao);
        if (acao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Acao> result = acaoRepository
            .findById(acao.getId())
            .map(existingAcao -> {
                if (acao.getDataAcao() != null) {
                    existingAcao.setDataAcao(acao.getDataAcao());
                }
                if (acao.getUsuarioCriacaoAcao() != null) {
                    existingAcao.setUsuarioCriacaoAcao(acao.getUsuarioCriacaoAcao());
                }
                if (acao.getPendente() != null) {
                    existingAcao.setPendente(acao.getPendente());
                }
                if (acao.getDataExecucaoAcao() != null) {
                    existingAcao.setDataExecucaoAcao(acao.getDataExecucaoAcao());
                }
                if (acao.getAtiva() != null) {
                    existingAcao.setAtiva(acao.getAtiva());
                }
                if (acao.getObservacoes() != null) {
                    existingAcao.setObservacoes(acao.getObservacoes());
                }

                return existingAcao;
            })
            .map(acaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, acao.getId().toString())
        );
    }

    /**
     * {@code GET  /acaos} : get all the acaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acaos in body.
     */
    @GetMapping("/acaos")
    public List<Acao> getAllAcaos() {
        log.debug("REST request to get all Acaos");
        return acaoRepository.findAll();
    }

    /**
     * {@code GET  /acaos/:id} : get the "id" acao.
     *
     * @param id the id of the acao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acaos/{id}")
    public ResponseEntity<Acao> getAcao(@PathVariable Long id) {
        log.debug("REST request to get Acao : {}", id);
        Optional<Acao> acao = acaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acao);
    }

    /**
     * {@code DELETE  /acaos/:id} : delete the "id" acao.
     *
     * @param id the id of the acao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acaos/{id}")
    public ResponseEntity<Void> deleteAcao(@PathVariable Long id) {
        log.debug("REST request to delete Acao : {}", id);
        acaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
