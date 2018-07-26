package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.ThreatSubType;
import com.empleodigital.murcia.majerithz.repository.ThreatSubTypeRepository;
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
 * REST controller for managing ThreatSubType.
 */
@RestController
@RequestMapping("/api")
public class ThreatSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(ThreatSubTypeResource.class);

    private static final String ENTITY_NAME = "threatSubType";

    private final ThreatSubTypeRepository threatSubTypeRepository;

    public ThreatSubTypeResource(ThreatSubTypeRepository threatSubTypeRepository) {
        this.threatSubTypeRepository = threatSubTypeRepository;
    }

    /**
     * POST  /threat-sub-types : Create a new threatSubType.
     *
     * @param threatSubType the threatSubType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new threatSubType, or with status 400 (Bad Request) if the threatSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/threat-sub-types")
    @Timed
    public ResponseEntity<ThreatSubType> createThreatSubType(@Valid @RequestBody ThreatSubType threatSubType) throws URISyntaxException {
        log.debug("REST request to save ThreatSubType : {}", threatSubType);
        if (threatSubType.getId() != null) {
            throw new BadRequestAlertException("A new threatSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThreatSubType result = threatSubTypeRepository.save(threatSubType);
        return ResponseEntity.created(new URI("/api/threat-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /threat-sub-types : Updates an existing threatSubType.
     *
     * @param threatSubType the threatSubType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated threatSubType,
     * or with status 400 (Bad Request) if the threatSubType is not valid,
     * or with status 500 (Internal Server Error) if the threatSubType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/threat-sub-types")
    @Timed
    public ResponseEntity<ThreatSubType> updateThreatSubType(@Valid @RequestBody ThreatSubType threatSubType) throws URISyntaxException {
        log.debug("REST request to update ThreatSubType : {}", threatSubType);
        if (threatSubType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThreatSubType result = threatSubTypeRepository.save(threatSubType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, threatSubType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /threat-sub-types : get all the threatSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of threatSubTypes in body
     */
    @GetMapping("/threat-sub-types")
    @Timed
    public List<ThreatSubType> getAllThreatSubTypes() {
        log.debug("REST request to get all ThreatSubTypes");
        return threatSubTypeRepository.findAll();
    }

    /**
     * GET  /threat-sub-types/:id : get the "id" threatSubType.
     *
     * @param id the id of the threatSubType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the threatSubType, or with status 404 (Not Found)
     */
    @GetMapping("/threat-sub-types/{id}")
    @Timed
    public ResponseEntity<ThreatSubType> getThreatSubType(@PathVariable Long id) {
        log.debug("REST request to get ThreatSubType : {}", id);
        Optional<ThreatSubType> threatSubType = threatSubTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(threatSubType);
    }

    /**
     * DELETE  /threat-sub-types/:id : delete the "id" threatSubType.
     *
     * @param id the id of the threatSubType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/threat-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteThreatSubType(@PathVariable Long id) {
        log.debug("REST request to delete ThreatSubType : {}", id);

        threatSubTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
