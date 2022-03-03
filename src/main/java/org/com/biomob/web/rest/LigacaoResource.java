package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.com.biomob.domain.Ligacao;
import org.com.biomob.repository.LigacaoRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.Ligacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LigacaoResource {

    private final Logger log = LoggerFactory.getLogger(LigacaoResource.class);

    private static final String ENTITY_NAME = "ligacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigacaoRepository ligacaoRepository;

    public LigacaoResource(LigacaoRepository ligacaoRepository) {
        this.ligacaoRepository = ligacaoRepository;
    }

    /**
     * {@code POST  /ligacaos} : Create a new ligacao.
     *
     * @param ligacao the ligacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligacao, or with status {@code 400 (Bad Request)} if the ligacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligacaos")
    public ResponseEntity<Ligacao> createLigacao(@RequestBody Ligacao ligacao) throws URISyntaxException {
        log.debug("REST request to save Ligacao : {}", ligacao);
        if (ligacao.getId() != null) {
            throw new BadRequestAlertException("A new ligacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ligacao result = ligacaoRepository.save(ligacao);
        return ResponseEntity
            .created(new URI("/api/ligacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligacaos/:id} : Updates an existing ligacao.
     *
     * @param id the id of the ligacao to save.
     * @param ligacao the ligacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligacao,
     * or with status {@code 400 (Bad Request)} if the ligacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligacaos/{id}")
    public ResponseEntity<Ligacao> updateLigacao(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ligacao ligacao)
        throws URISyntaxException {
        log.debug("REST request to update Ligacao : {}, {}", id, ligacao);
        if (ligacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ligacao result = ligacaoRepository.save(ligacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligacaos/:id} : Partial updates given fields of an existing ligacao, field will ignore if it is null
     *
     * @param id the id of the ligacao to save.
     * @param ligacao the ligacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligacao,
     * or with status {@code 400 (Bad Request)} if the ligacao is not valid,
     * or with status {@code 404 (Not Found)} if the ligacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ligacao> partialUpdateLigacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ligacao ligacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ligacao partially : {}, {}", id, ligacao);
        if (ligacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ligacao> result = ligacaoRepository
            .findById(ligacao.getId())
            .map(existingLigacao -> {
                return existingLigacao;
            })
            .map(ligacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligacao.getId().toString())
        );
    }

    /**
     * {@code GET  /ligacaos} : get all the ligacaos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligacaos in body.
     */
    @GetMapping("/ligacaos")
    public List<Ligacao> getAllLigacaos(@RequestParam(required = false) String filter) {
        if ("acao-is-null".equals(filter)) {
            log.debug("REST request to get all Ligacaos where acao is null");
            return StreamSupport
                .stream(ligacaoRepository.findAll().spliterator(), false)
                .filter(ligacao -> ligacao.getAcao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Ligacaos");
        return ligacaoRepository.findAll();
    }

    /**
     * {@code GET  /ligacaos/:id} : get the "id" ligacao.
     *
     * @param id the id of the ligacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligacaos/{id}")
    public ResponseEntity<Ligacao> getLigacao(@PathVariable Long id) {
        log.debug("REST request to get Ligacao : {}", id);
        Optional<Ligacao> ligacao = ligacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ligacao);
    }

    /**
     * {@code DELETE  /ligacaos/:id} : delete the "id" ligacao.
     *
     * @param id the id of the ligacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligacaos/{id}")
    public ResponseEntity<Void> deleteLigacao(@PathVariable Long id) {
        log.debug("REST request to delete Ligacao : {}", id);
        ligacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
