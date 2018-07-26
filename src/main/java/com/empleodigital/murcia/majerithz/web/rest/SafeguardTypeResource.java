package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.SafeguardType;
import com.empleodigital.murcia.majerithz.repository.SafeguardTypeRepository;
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
 * REST controller for managing SafeguardType.
 */
@RestController
@RequestMapping("/api")
public class SafeguardTypeResource {

    private final Logger log = LoggerFactory.getLogger(SafeguardTypeResource.class);

    private static final String ENTITY_NAME = "safeguardType";

    private final SafeguardTypeRepository safeguardTypeRepository;

    public SafeguardTypeResource(SafeguardTypeRepository safeguardTypeRepository) {
        this.safeguardTypeRepository = safeguardTypeRepository;
    }

    /**
     * POST  /safeguard-types : Create a new safeguardType.
     *
     * @param safeguardType the safeguardType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new safeguardType, or with status 400 (Bad Request) if the safeguardType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/safeguard-types")
    @Timed
    public ResponseEntity<SafeguardType> createSafeguardType(@Valid @RequestBody SafeguardType safeguardType) throws URISyntaxException {
        log.debug("REST request to save SafeguardType : {}", safeguardType);
        if (safeguardType.getId() != null) {
            throw new BadRequestAlertException("A new safeguardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeguardType result = safeguardTypeRepository.save(safeguardType);
        return ResponseEntity.created(new URI("/api/safeguard-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /safeguard-types : Updates an existing safeguardType.
     *
     * @param safeguardType the safeguardType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated safeguardType,
     * or with status 400 (Bad Request) if the safeguardType is not valid,
     * or with status 500 (Internal Server Error) if the safeguardType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/safeguard-types")
    @Timed
    public ResponseEntity<SafeguardType> updateSafeguardType(@Valid @RequestBody SafeguardType safeguardType) throws URISyntaxException {
        log.debug("REST request to update SafeguardType : {}", safeguardType);
        if (safeguardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SafeguardType result = safeguardTypeRepository.save(safeguardType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, safeguardType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /safeguard-types : get all the safeguardTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of safeguardTypes in body
     */
    @GetMapping("/safeguard-types")
    @Timed
    public List<SafeguardType> getAllSafeguardTypes() {
        log.debug("REST request to get all SafeguardTypes");
        return safeguardTypeRepository.findAll();
    }

    /**
     * GET  /safeguard-types/:id : get the "id" safeguardType.
     *
     * @param id the id of the safeguardType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the safeguardType, or with status 404 (Not Found)
     */
    @GetMapping("/safeguard-types/{id}")
    @Timed
    public ResponseEntity<SafeguardType> getSafeguardType(@PathVariable Long id) {
        log.debug("REST request to get SafeguardType : {}", id);
        Optional<SafeguardType> safeguardType = safeguardTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(safeguardType);
    }

    /**
     * DELETE  /safeguard-types/:id : delete the "id" safeguardType.
     *
     * @param id the id of the safeguardType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/safeguard-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSafeguardType(@PathVariable Long id) {
        log.debug("REST request to delete SafeguardType : {}", id);

        safeguardTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
