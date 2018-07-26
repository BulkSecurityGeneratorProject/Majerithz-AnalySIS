package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.ThreatType;
import com.empleodigital.murcia.majerithz.repository.ThreatTypeRepository;
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
 * REST controller for managing ThreatType.
 */
@RestController
@RequestMapping("/api")
public class ThreatTypeResource {

    private final Logger log = LoggerFactory.getLogger(ThreatTypeResource.class);

    private static final String ENTITY_NAME = "threatType";

    private final ThreatTypeRepository threatTypeRepository;

    public ThreatTypeResource(ThreatTypeRepository threatTypeRepository) {
        this.threatTypeRepository = threatTypeRepository;
    }

    /**
     * POST  /threat-types : Create a new threatType.
     *
     * @param threatType the threatType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new threatType, or with status 400 (Bad Request) if the threatType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/threat-types")
    @Timed
    public ResponseEntity<ThreatType> createThreatType(@Valid @RequestBody ThreatType threatType) throws URISyntaxException {
        log.debug("REST request to save ThreatType : {}", threatType);
        if (threatType.getId() != null) {
            throw new BadRequestAlertException("A new threatType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThreatType result = threatTypeRepository.save(threatType);
        return ResponseEntity.created(new URI("/api/threat-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /threat-types : Updates an existing threatType.
     *
     * @param threatType the threatType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated threatType,
     * or with status 400 (Bad Request) if the threatType is not valid,
     * or with status 500 (Internal Server Error) if the threatType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/threat-types")
    @Timed
    public ResponseEntity<ThreatType> updateThreatType(@Valid @RequestBody ThreatType threatType) throws URISyntaxException {
        log.debug("REST request to update ThreatType : {}", threatType);
        if (threatType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThreatType result = threatTypeRepository.save(threatType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, threatType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /threat-types : get all the threatTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of threatTypes in body
     */
    @GetMapping("/threat-types")
    @Timed
    public List<ThreatType> getAllThreatTypes() {
        log.debug("REST request to get all ThreatTypes");
        return threatTypeRepository.findAll();
    }

    /**
     * GET  /threat-types/:id : get the "id" threatType.
     *
     * @param id the id of the threatType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the threatType, or with status 404 (Not Found)
     */
    @GetMapping("/threat-types/{id}")
    @Timed
    public ResponseEntity<ThreatType> getThreatType(@PathVariable Long id) {
        log.debug("REST request to get ThreatType : {}", id);
        Optional<ThreatType> threatType = threatTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(threatType);
    }

    /**
     * DELETE  /threat-types/:id : delete the "id" threatType.
     *
     * @param id the id of the threatType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/threat-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteThreatType(@PathVariable Long id) {
        log.debug("REST request to delete ThreatType : {}", id);

        threatTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
