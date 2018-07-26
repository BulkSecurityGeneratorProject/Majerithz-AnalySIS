package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.ExistingSafeguards;
import com.empleodigital.murcia.majerithz.repository.ExistingSafeguardsRepository;
import com.empleodigital.murcia.majerithz.web.rest.errors.BadRequestAlertException;
import com.empleodigital.murcia.majerithz.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExistingSafeguards.
 */
@RestController
@RequestMapping("/api")
public class ExistingSafeguardsResource {

    private final Logger log = LoggerFactory.getLogger(ExistingSafeguardsResource.class);

    private static final String ENTITY_NAME = "existingSafeguards";

    private final ExistingSafeguardsRepository existingSafeguardsRepository;

    public ExistingSafeguardsResource(ExistingSafeguardsRepository existingSafeguardsRepository) {
        this.existingSafeguardsRepository = existingSafeguardsRepository;
    }

    /**
     * POST  /existing-safeguards : Create a new existingSafeguards.
     *
     * @param existingSafeguards the existingSafeguards to create
     * @return the ResponseEntity with status 201 (Created) and with body the new existingSafeguards, or with status 400 (Bad Request) if the existingSafeguards has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/existing-safeguards")
    @Timed
    public ResponseEntity<ExistingSafeguards> createExistingSafeguards(@Valid @RequestBody ExistingSafeguards existingSafeguards) throws URISyntaxException {
        log.debug("REST request to save ExistingSafeguards : {}", existingSafeguards);
        if (existingSafeguards.getId() != null) {
            throw new BadRequestAlertException("A new existingSafeguards cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExistingSafeguards result = existingSafeguardsRepository.save(existingSafeguards);
        return ResponseEntity.created(new URI("/api/existing-safeguards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /existing-safeguards : Updates an existing existingSafeguards.
     *
     * @param existingSafeguards the existingSafeguards to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated existingSafeguards,
     * or with status 400 (Bad Request) if the existingSafeguards is not valid,
     * or with status 500 (Internal Server Error) if the existingSafeguards couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/existing-safeguards")
    @Timed
    public ResponseEntity<ExistingSafeguards> updateExistingSafeguards(@Valid @RequestBody ExistingSafeguards existingSafeguards) throws URISyntaxException {
        log.debug("REST request to update ExistingSafeguards : {}", existingSafeguards);
        if (existingSafeguards.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExistingSafeguards result = existingSafeguardsRepository.save(existingSafeguards);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, existingSafeguards.getId().toString()))
            .body(result);
    }

    /**
     * GET  /existing-safeguards : get all the existingSafeguards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of existingSafeguards in body
     */
    @GetMapping("/existing-safeguards")
    @Timed
    public List<ExistingSafeguards> getAllExistingSafeguards() {
        log.debug("REST request to get all ExistingSafeguards");
        return existingSafeguardsRepository.findAll();
    }

    /**
     * GET  /existing-safeguards/:id : get the "id" existingSafeguards.
     *
     * @param id the id of the existingSafeguards to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the existingSafeguards, or with status 404 (Not Found)
     */
    @GetMapping("/existing-safeguards/{id}")
    @Timed
    public ResponseEntity<ExistingSafeguards> getExistingSafeguards(@PathVariable Long id) {
        log.debug("REST request to get ExistingSafeguards : {}", id);
        Optional<ExistingSafeguards> existingSafeguards = existingSafeguardsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(existingSafeguards);
    }

    /**
     * DELETE  /existing-safeguards/:id : delete the "id" existingSafeguards.
     *
     * @param id the id of the existingSafeguards to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/existing-safeguards/{id}")
    @Timed
    public ResponseEntity<Void> deleteExistingSafeguards(@PathVariable Long id) {
        log.debug("REST request to delete ExistingSafeguards : {}", id);

        existingSafeguardsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
