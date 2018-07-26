package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.Threat;
import com.empleodigital.murcia.majerithz.repository.ThreatRepository;
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
 * REST controller for managing Threat.
 */
@RestController
@RequestMapping("/api")
public class ThreatResource {

    private final Logger log = LoggerFactory.getLogger(ThreatResource.class);

    private static final String ENTITY_NAME = "threat";

    private final ThreatRepository threatRepository;

    public ThreatResource(ThreatRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    /**
     * POST  /threats : Create a new threat.
     *
     * @param threat the threat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new threat, or with status 400 (Bad Request) if the threat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/threats")
    @Timed
    public ResponseEntity<Threat> createThreat(@Valid @RequestBody Threat threat) throws URISyntaxException {
        log.debug("REST request to save Threat : {}", threat);
        if (threat.getId() != null) {
            throw new BadRequestAlertException("A new threat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Threat result = threatRepository.save(threat);
        return ResponseEntity.created(new URI("/api/threats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /threats : Updates an existing threat.
     *
     * @param threat the threat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated threat,
     * or with status 400 (Bad Request) if the threat is not valid,
     * or with status 500 (Internal Server Error) if the threat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/threats")
    @Timed
    public ResponseEntity<Threat> updateThreat(@Valid @RequestBody Threat threat) throws URISyntaxException {
        log.debug("REST request to update Threat : {}", threat);
        if (threat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Threat result = threatRepository.save(threat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, threat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /threats : get all the threats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of threats in body
     */
    @GetMapping("/threats")
    @Timed
    public List<Threat> getAllThreats() {
        log.debug("REST request to get all Threats");
        return threatRepository.findAll();
    }

    /**
     * GET  /threats/:id : get the "id" threat.
     *
     * @param id the id of the threat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the threat, or with status 404 (Not Found)
     */
    @GetMapping("/threats/{id}")
    @Timed
    public ResponseEntity<Threat> getThreat(@PathVariable Long id) {
        log.debug("REST request to get Threat : {}", id);
        Optional<Threat> threat = threatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(threat);
    }

    /**
     * DELETE  /threats/:id : delete the "id" threat.
     *
     * @param id the id of the threat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/threats/{id}")
    @Timed
    public ResponseEntity<Void> deleteThreat(@PathVariable Long id) {
        log.debug("REST request to delete Threat : {}", id);

        threatRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
