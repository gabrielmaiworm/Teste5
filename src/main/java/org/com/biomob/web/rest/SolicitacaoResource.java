package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.com.biomob.domain.Solicitacao;
import org.com.biomob.repository.SolicitacaoRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.Solicitacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SolicitacaoResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoResource.class);

    private static final String ENTITY_NAME = "solicitacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoResource(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    /**
     * {@code POST  /solicitacaos} : Create a new solicitacao.
     *
     * @param solicitacao the solicitacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacao, or with status {@code 400 (Bad Request)} if the solicitacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacaos")
    public ResponseEntity<Solicitacao> createSolicitacao(@RequestBody Solicitacao solicitacao) throws URISyntaxException {
        log.debug("REST request to save Solicitacao : {}", solicitacao);
        if (solicitacao.getId() != null) {
            throw new BadRequestAlertException("A new solicitacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Solicitacao result = solicitacaoRepository.save(solicitacao);
        return ResponseEntity
            .created(new URI("/api/solicitacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacaos/:id} : Updates an existing solicitacao.
     *
     * @param id the id of the solicitacao to save.
     * @param solicitacao the solicitacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacao,
     * or with status {@code 400 (Bad Request)} if the solicitacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacaos/{id}")
    public ResponseEntity<Solicitacao> updateSolicitacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Solicitacao solicitacao
    ) throws URISyntaxException {
        log.debug("REST request to update Solicitacao : {}, {}", id, solicitacao);
        if (solicitacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Solicitacao result = solicitacaoRepository.save(solicitacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solicitacaos/:id} : Partial updates given fields of an existing solicitacao, field will ignore if it is null
     *
     * @param id the id of the solicitacao to save.
     * @param solicitacao the solicitacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacao,
     * or with status {@code 400 (Bad Request)} if the solicitacao is not valid,
     * or with status {@code 404 (Not Found)} if the solicitacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the solicitacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solicitacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Solicitacao> partialUpdateSolicitacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Solicitacao solicitacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Solicitacao partially : {}, {}", id, solicitacao);
        if (solicitacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Solicitacao> result = solicitacaoRepository
            .findById(solicitacao.getId())
            .map(existingSolicitacao -> {
                if (solicitacao.getSolicitante() != null) {
                    existingSolicitacao.setSolicitante(solicitacao.getSolicitante());
                }
                if (solicitacao.getAnonima() != null) {
                    existingSolicitacao.setAnonima(solicitacao.getAnonima());
                }
                if (solicitacao.getDataSolicitacao() != null) {
                    existingSolicitacao.setDataSolicitacao(solicitacao.getDataSolicitacao());
                }
                if (solicitacao.getEnderecoEntrega() != null) {
                    existingSolicitacao.setEnderecoEntrega(solicitacao.getEnderecoEntrega());
                }
                if (solicitacao.getLocalDeEntrega() != null) {
                    existingSolicitacao.setLocalDeEntrega(solicitacao.getLocalDeEntrega());
                }
                if (solicitacao.getDataAprovacao() != null) {
                    existingSolicitacao.setDataAprovacao(solicitacao.getDataAprovacao());
                }
                if (solicitacao.getAprovado() != null) {
                    existingSolicitacao.setAprovado(solicitacao.getAprovado());
                }
                if (solicitacao.getAtiva() != null) {
                    existingSolicitacao.setAtiva(solicitacao.getAtiva());
                }

                return existingSolicitacao;
            })
            .map(solicitacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacao.getId().toString())
        );
    }

    /**
     * {@code GET  /solicitacaos} : get all the solicitacaos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaos in body.
     */
    @GetMapping("/solicitacaos")
    public List<Solicitacao> getAllSolicitacaos(@RequestParam(required = false) String filter) {
        if ("acao-is-null".equals(filter)) {
            log.debug("REST request to get all Solicitacaos where acao is null");
            return StreamSupport
                .stream(solicitacaoRepository.findAll().spliterator(), false)
                .filter(solicitacao -> solicitacao.getAcao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Solicitacaos");
        return solicitacaoRepository.findAll();
    }

    /**
     * {@code GET  /solicitacaos/:id} : get the "id" solicitacao.
     *
     * @param id the id of the solicitacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacaos/{id}")
    public ResponseEntity<Solicitacao> getSolicitacao(@PathVariable Long id) {
        log.debug("REST request to get Solicitacao : {}", id);
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(solicitacao);
    }

    /**
     * {@code DELETE  /solicitacaos/:id} : delete the "id" solicitacao.
     *
     * @param id the id of the solicitacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacaos/{id}")
    public ResponseEntity<Void> deleteSolicitacao(@PathVariable Long id) {
        log.debug("REST request to delete Solicitacao : {}", id);
        solicitacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
