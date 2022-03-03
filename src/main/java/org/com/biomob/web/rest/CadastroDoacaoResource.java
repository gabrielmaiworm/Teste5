package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.com.biomob.domain.CadastroDoacao;
import org.com.biomob.repository.CadastroDoacaoRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.CadastroDoacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CadastroDoacaoResource {

    private final Logger log = LoggerFactory.getLogger(CadastroDoacaoResource.class);

    private static final String ENTITY_NAME = "cadastroDoacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CadastroDoacaoRepository cadastroDoacaoRepository;

    public CadastroDoacaoResource(CadastroDoacaoRepository cadastroDoacaoRepository) {
        this.cadastroDoacaoRepository = cadastroDoacaoRepository;
    }

    /**
     * {@code POST  /cadastro-doacaos} : Create a new cadastroDoacao.
     *
     * @param cadastroDoacao the cadastroDoacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cadastroDoacao, or with status {@code 400 (Bad Request)} if the cadastroDoacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cadastro-doacaos")
    public ResponseEntity<CadastroDoacao> createCadastroDoacao(@RequestBody CadastroDoacao cadastroDoacao) throws URISyntaxException {
        log.debug("REST request to save CadastroDoacao : {}", cadastroDoacao);
        if (cadastroDoacao.getId() != null) {
            throw new BadRequestAlertException("A new cadastroDoacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CadastroDoacao result = cadastroDoacaoRepository.save(cadastroDoacao);
        return ResponseEntity
            .created(new URI("/api/cadastro-doacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cadastro-doacaos/:id} : Updates an existing cadastroDoacao.
     *
     * @param id the id of the cadastroDoacao to save.
     * @param cadastroDoacao the cadastroDoacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroDoacao,
     * or with status {@code 400 (Bad Request)} if the cadastroDoacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cadastroDoacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cadastro-doacaos/{id}")
    public ResponseEntity<CadastroDoacao> updateCadastroDoacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroDoacao cadastroDoacao
    ) throws URISyntaxException {
        log.debug("REST request to update CadastroDoacao : {}, {}", id, cadastroDoacao);
        if (cadastroDoacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroDoacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroDoacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CadastroDoacao result = cadastroDoacaoRepository.save(cadastroDoacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroDoacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cadastro-doacaos/:id} : Partial updates given fields of an existing cadastroDoacao, field will ignore if it is null
     *
     * @param id the id of the cadastroDoacao to save.
     * @param cadastroDoacao the cadastroDoacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroDoacao,
     * or with status {@code 400 (Bad Request)} if the cadastroDoacao is not valid,
     * or with status {@code 404 (Not Found)} if the cadastroDoacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the cadastroDoacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cadastro-doacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CadastroDoacao> partialUpdateCadastroDoacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroDoacao cadastroDoacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update CadastroDoacao partially : {}, {}", id, cadastroDoacao);
        if (cadastroDoacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroDoacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroDoacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CadastroDoacao> result = cadastroDoacaoRepository
            .findById(cadastroDoacao.getId())
            .map(existingCadastroDoacao -> {
                if (cadastroDoacao.getDoacaoAnonima() != null) {
                    existingCadastroDoacao.setDoacaoAnonima(cadastroDoacao.getDoacaoAnonima());
                }
                if (cadastroDoacao.getRealizaEntrega() != null) {
                    existingCadastroDoacao.setRealizaEntrega(cadastroDoacao.getRealizaEntrega());
                }
                if (cadastroDoacao.getDataDoacao() != null) {
                    existingCadastroDoacao.setDataDoacao(cadastroDoacao.getDataDoacao());
                }
                if (cadastroDoacao.getLogradouro() != null) {
                    existingCadastroDoacao.setLogradouro(cadastroDoacao.getLogradouro());
                }
                if (cadastroDoacao.getNumero() != null) {
                    existingCadastroDoacao.setNumero(cadastroDoacao.getNumero());
                }
                if (cadastroDoacao.getBairro() != null) {
                    existingCadastroDoacao.setBairro(cadastroDoacao.getBairro());
                }
                if (cadastroDoacao.getCidade() != null) {
                    existingCadastroDoacao.setCidade(cadastroDoacao.getCidade());
                }
                if (cadastroDoacao.getCep() != null) {
                    existingCadastroDoacao.setCep(cadastroDoacao.getCep());
                }
                if (cadastroDoacao.getEstado() != null) {
                    existingCadastroDoacao.setEstado(cadastroDoacao.getEstado());
                }
                if (cadastroDoacao.getPais() != null) {
                    existingCadastroDoacao.setPais(cadastroDoacao.getPais());
                }
                if (cadastroDoacao.getComplemento() != null) {
                    existingCadastroDoacao.setComplemento(cadastroDoacao.getComplemento());
                }

                return existingCadastroDoacao;
            })
            .map(cadastroDoacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroDoacao.getId().toString())
        );
    }

    /**
     * {@code GET  /cadastro-doacaos} : get all the cadastroDoacaos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadastroDoacaos in body.
     */
    @GetMapping("/cadastro-doacaos")
    public List<CadastroDoacao> getAllCadastroDoacaos(@RequestParam(required = false) String filter) {
        if ("acao-is-null".equals(filter)) {
            log.debug("REST request to get all CadastroDoacaos where acao is null");
            return StreamSupport
                .stream(cadastroDoacaoRepository.findAll().spliterator(), false)
                .filter(cadastroDoacao -> cadastroDoacao.getAcao() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all CadastroDoacaos");
        return cadastroDoacaoRepository.findAll();
    }

    /**
     * {@code GET  /cadastro-doacaos/:id} : get the "id" cadastroDoacao.
     *
     * @param id the id of the cadastroDoacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cadastroDoacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cadastro-doacaos/{id}")
    public ResponseEntity<CadastroDoacao> getCadastroDoacao(@PathVariable Long id) {
        log.debug("REST request to get CadastroDoacao : {}", id);
        Optional<CadastroDoacao> cadastroDoacao = cadastroDoacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cadastroDoacao);
    }

    /**
     * {@code DELETE  /cadastro-doacaos/:id} : delete the "id" cadastroDoacao.
     *
     * @param id the id of the cadastroDoacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cadastro-doacaos/{id}")
    public ResponseEntity<Void> deleteCadastroDoacao(@PathVariable Long id) {
        log.debug("REST request to delete CadastroDoacao : {}", id);
        cadastroDoacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
