package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.Safeguard;
import com.empleodigital.murcia.majerithz.repository.SafeguardRepository;
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
 * REST controller for managing Safeguard.
 */
@RestController
@RequestMapping("/api")
public class SafeguardResource {

    private final Logger log = LoggerFactory.getLogger(SafeguardResource.class);

    private static final String ENTITY_NAME = "safeguard";

    private final SafeguardRepository safeguardRepository;

    public SafeguardResource(SafeguardRepository safeguardRepository) {
        this.safeguardRepository = safeguardRepository;
    }

    /**
     * POST  /safeguards : Create a new safeguard.
     *
     * @param safeguard the safeguard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new safeguard, or with status 400 (Bad Request) if the safeguard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/safeguards")
    @Timed
    public ResponseEntity<Safeguard> createSafeguard(@Valid @RequestBody Safeguard safeguard) throws URISyntaxException {
        log.debug("REST request to save Safeguard : {}", safeguard);
        if (safeguard.getId() != null) {
            throw new BadRequestAlertException("A new safeguard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Safeguard result = safeguardRepository.save(safeguard);
        return ResponseEntity.created(new URI("/api/safeguards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /safeguards : Updates an existing safeguard.
     *
     * @param safeguard the safeguard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated safeguard,
     * or with status 400 (Bad Request) if the safeguard is not valid,
     * or with status 500 (Internal Server Error) if the safeguard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/safeguards")
    @Timed
    public ResponseEntity<Safeguard> updateSafeguard(@Valid @RequestBody Safeguard safeguard) throws URISyntaxException {
        log.debug("REST request to update Safeguard : {}", safeguard);
        if (safeguard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Safeguard result = safeguardRepository.save(safeguard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, safeguard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /safeguards : get all the safeguards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of safeguards in body
     */
    @GetMapping("/safeguards")
    @Timed
    public List<Safeguard> getAllSafeguards() {
        log.debug("REST request to get all Safeguards");
        return safeguardRepository.findAll();
    }

    /**
     * GET  /safeguards/:id : get the "id" safeguard.
     *
     * @param id the id of the safeguard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the safeguard, or with status 404 (Not Found)
     */
    @GetMapping("/safeguards/{id}")
    @Timed
    public ResponseEntity<Safeguard> getSafeguard(@PathVariable Long id) {
        log.debug("REST request to get Safeguard : {}", id);
        Optional<Safeguard> safeguard = safeguardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(safeguard);
    }

    /**
     * DELETE  /safeguards/:id : delete the "id" safeguard.
     *
     * @param id the id of the safeguard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/safeguards/{id}")
    @Timed
    public ResponseEntity<Void> deleteSafeguard(@PathVariable Long id) {
        log.debug("REST request to delete Safeguard : {}", id);

        safeguardRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
