package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.Dependence;
import com.empleodigital.murcia.majerithz.repository.DependenceRepository;
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
 * REST controller for managing Dependence.
 */
@RestController
@RequestMapping("/api")
public class DependenceResource {

    private final Logger log = LoggerFactory.getLogger(DependenceResource.class);

    private static final String ENTITY_NAME = "dependence";

    private final DependenceRepository dependenceRepository;

    public DependenceResource(DependenceRepository dependenceRepository) {
        this.dependenceRepository = dependenceRepository;
    }

    /**
     * POST  /dependences : Create a new dependence.
     *
     * @param dependence the dependence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dependence, or with status 400 (Bad Request) if the dependence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dependences")
    @Timed
    public ResponseEntity<Dependence> createDependence(@Valid @RequestBody Dependence dependence) throws URISyntaxException {
        log.debug("REST request to save Dependence : {}", dependence);
        if (dependence.getId() != null) {
            throw new BadRequestAlertException("A new dependence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependence result = dependenceRepository.save(dependence);
        return ResponseEntity.created(new URI("/api/dependences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dependences : Updates an existing dependence.
     *
     * @param dependence the dependence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dependence,
     * or with status 400 (Bad Request) if the dependence is not valid,
     * or with status 500 (Internal Server Error) if the dependence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dependences")
    @Timed
    public ResponseEntity<Dependence> updateDependence(@Valid @RequestBody Dependence dependence) throws URISyntaxException {
        log.debug("REST request to update Dependence : {}", dependence);
        if (dependence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dependence result = dependenceRepository.save(dependence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dependence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dependences : get all the dependences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dependences in body
     */
    @GetMapping("/dependences")
    @Timed
    public List<Dependence> getAllDependences() {
        log.debug("REST request to get all Dependences");
        return dependenceRepository.findAll();
    }

    /**
     * GET  /dependences/:id : get the "id" dependence.
     *
     * @param id the id of the dependence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dependence, or with status 404 (Not Found)
     */
    @GetMapping("/dependences/{id}")
    @Timed
    public ResponseEntity<Dependence> getDependence(@PathVariable Long id) {
        log.debug("REST request to get Dependence : {}", id);
        Optional<Dependence> dependence = dependenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dependence);
    }

    /**
     * DELETE  /dependences/:id : delete the "id" dependence.
     *
     * @param id the id of the dependence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dependences/{id}")
    @Timed
    public ResponseEntity<Void> deleteDependence(@PathVariable Long id) {
        log.debug("REST request to delete Dependence : {}", id);

        dependenceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
