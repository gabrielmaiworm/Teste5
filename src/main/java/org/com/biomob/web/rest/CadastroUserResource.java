package org.com.biomob.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.com.biomob.domain.CadastroUser;
import org.com.biomob.repository.CadastroUserRepository;
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
 * REST controller for managing {@link org.com.biomob.domain.CadastroUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CadastroUserResource {

    private final Logger log = LoggerFactory.getLogger(CadastroUserResource.class);

    private static final String ENTITY_NAME = "cadastroUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CadastroUserRepository cadastroUserRepository;

    public CadastroUserResource(CadastroUserRepository cadastroUserRepository) {
        this.cadastroUserRepository = cadastroUserRepository;
    }

    /**
     * {@code POST  /cadastro-users} : Create a new cadastroUser.
     *
     * @param cadastroUser the cadastroUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cadastroUser, or with status {@code 400 (Bad Request)} if the cadastroUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cadastro-users")
    public ResponseEntity<CadastroUser> createCadastroUser(@RequestBody CadastroUser cadastroUser) throws URISyntaxException {
        log.debug("REST request to save CadastroUser : {}", cadastroUser);
        if (cadastroUser.getId() != null) {
            throw new BadRequestAlertException("A new cadastroUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CadastroUser result = cadastroUserRepository.save(cadastroUser);
        return ResponseEntity
            .created(new URI("/api/cadastro-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cadastro-users/:id} : Updates an existing cadastroUser.
     *
     * @param id the id of the cadastroUser to save.
     * @param cadastroUser the cadastroUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroUser,
     * or with status {@code 400 (Bad Request)} if the cadastroUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cadastroUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cadastro-users/{id}")
    public ResponseEntity<CadastroUser> updateCadastroUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroUser cadastroUser
    ) throws URISyntaxException {
        log.debug("REST request to update CadastroUser : {}, {}", id, cadastroUser);
        if (cadastroUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CadastroUser result = cadastroUserRepository.save(cadastroUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cadastro-users/:id} : Partial updates given fields of an existing cadastroUser, field will ignore if it is null
     *
     * @param id the id of the cadastroUser to save.
     * @param cadastroUser the cadastroUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadastroUser,
     * or with status {@code 400 (Bad Request)} if the cadastroUser is not valid,
     * or with status {@code 404 (Not Found)} if the cadastroUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the cadastroUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cadastro-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CadastroUser> partialUpdateCadastroUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CadastroUser cadastroUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update CadastroUser partially : {}, {}", id, cadastroUser);
        if (cadastroUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cadastroUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cadastroUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CadastroUser> result = cadastroUserRepository
            .findById(cadastroUser.getId())
            .map(existingCadastroUser -> {
                if (cadastroUser.getNome() != null) {
                    existingCadastroUser.setNome(cadastroUser.getNome());
                }
                if (cadastroUser.getTelefone() != null) {
                    existingCadastroUser.setTelefone(cadastroUser.getTelefone());
                }
                if (cadastroUser.getTipo() != null) {
                    existingCadastroUser.setTipo(cadastroUser.getTipo());
                }
                if (cadastroUser.getPais() != null) {
                    existingCadastroUser.setPais(cadastroUser.getPais());
                }
                if (cadastroUser.getEstado() != null) {
                    existingCadastroUser.setEstado(cadastroUser.getEstado());
                }
                if (cadastroUser.getCidade() != null) {
                    existingCadastroUser.setCidade(cadastroUser.getCidade());
                }
                if (cadastroUser.getBairro() != null) {
                    existingCadastroUser.setBairro(cadastroUser.getBairro());
                }
                if (cadastroUser.getCep() != null) {
                    existingCadastroUser.setCep(cadastroUser.getCep());
                }
                if (cadastroUser.getLogradouro() != null) {
                    existingCadastroUser.setLogradouro(cadastroUser.getLogradouro());
                }
                if (cadastroUser.getNumero() != null) {
                    existingCadastroUser.setNumero(cadastroUser.getNumero());
                }
                if (cadastroUser.getComplemento() != null) {
                    existingCadastroUser.setComplemento(cadastroUser.getComplemento());
                }

                return existingCadastroUser;
            })
            .map(cadastroUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadastroUser.getId().toString())
        );
    }

    /**
     * {@code GET  /cadastro-users} : get all the cadastroUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadastroUsers in body.
     */
    @GetMapping("/cadastro-users")
    public List<CadastroUser> getAllCadastroUsers() {
        log.debug("REST request to get all CadastroUsers");
        return cadastroUserRepository.findAll();
    }

    /**
     * {@code GET  /cadastro-users/:id} : get the "id" cadastroUser.
     *
     * @param id the id of the cadastroUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cadastroUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cadastro-users/{id}")
    public ResponseEntity<CadastroUser> getCadastroUser(@PathVariable Long id) {
        log.debug("REST request to get CadastroUser : {}", id);
        Optional<CadastroUser> cadastroUser = cadastroUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cadastroUser);
    }

    /**
     * {@code DELETE  /cadastro-users/:id} : delete the "id" cadastroUser.
     *
     * @param id the id of the cadastroUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cadastro-users/{id}")
    public ResponseEntity<Void> deleteCadastroUser(@PathVariable Long id) {
        log.debug("REST request to delete CadastroUser : {}", id);
        cadastroUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
