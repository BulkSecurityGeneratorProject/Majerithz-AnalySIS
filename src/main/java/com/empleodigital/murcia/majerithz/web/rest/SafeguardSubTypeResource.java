package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.SafeguardSubType;
import com.empleodigital.murcia.majerithz.repository.SafeguardSubTypeRepository;
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
 * REST controller for managing SafeguardSubType.
 */
@RestController
@RequestMapping("/api")
public class SafeguardSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(SafeguardSubTypeResource.class);

    private static final String ENTITY_NAME = "safeguardSubType";

    private final SafeguardSubTypeRepository safeguardSubTypeRepository;

    public SafeguardSubTypeResource(SafeguardSubTypeRepository safeguardSubTypeRepository) {
        this.safeguardSubTypeRepository = safeguardSubTypeRepository;
    }

    /**
     * POST  /safeguard-sub-types : Create a new safeguardSubType.
     *
     * @param safeguardSubType the safeguardSubType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new safeguardSubType, or with status 400 (Bad Request) if the safeguardSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/safeguard-sub-types")
    @Timed
    public ResponseEntity<SafeguardSubType> createSafeguardSubType(@Valid @RequestBody SafeguardSubType safeguardSubType) throws URISyntaxException {
        log.debug("REST request to save SafeguardSubType : {}", safeguardSubType);
        if (safeguardSubType.getId() != null) {
            throw new BadRequestAlertException("A new safeguardSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeguardSubType result = safeguardSubTypeRepository.save(safeguardSubType);
        return ResponseEntity.created(new URI("/api/safeguard-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /safeguard-sub-types : Updates an existing safeguardSubType.
     *
     * @param safeguardSubType the safeguardSubType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated safeguardSubType,
     * or with status 400 (Bad Request) if the safeguardSubType is not valid,
     * or with status 500 (Internal Server Error) if the safeguardSubType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/safeguard-sub-types")
    @Timed
    public ResponseEntity<SafeguardSubType> updateSafeguardSubType(@Valid @RequestBody SafeguardSubType safeguardSubType) throws URISyntaxException {
        log.debug("REST request to update SafeguardSubType : {}", safeguardSubType);
        if (safeguardSubType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SafeguardSubType result = safeguardSubTypeRepository.save(safeguardSubType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, safeguardSubType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /safeguard-sub-types : get all the safeguardSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of safeguardSubTypes in body
     */
    @GetMapping("/safeguard-sub-types")
    @Timed
    public List<SafeguardSubType> getAllSafeguardSubTypes() {
        log.debug("REST request to get all SafeguardSubTypes");
        return safeguardSubTypeRepository.findAll();
    }

    /**
     * GET  /safeguard-sub-types/:id : get the "id" safeguardSubType.
     *
     * @param id the id of the safeguardSubType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the safeguardSubType, or with status 404 (Not Found)
     */
    @GetMapping("/safeguard-sub-types/{id}")
    @Timed
    public ResponseEntity<SafeguardSubType> getSafeguardSubType(@PathVariable Long id) {
        log.debug("REST request to get SafeguardSubType : {}", id);
        Optional<SafeguardSubType> safeguardSubType = safeguardSubTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(safeguardSubType);
    }

    /**
     * DELETE  /safeguard-sub-types/:id : delete the "id" safeguardSubType.
     *
     * @param id the id of the safeguardSubType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/safeguard-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSafeguardSubType(@PathVariable Long id) {
        log.debug("REST request to delete SafeguardSubType : {}", id);

        safeguardSubTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
