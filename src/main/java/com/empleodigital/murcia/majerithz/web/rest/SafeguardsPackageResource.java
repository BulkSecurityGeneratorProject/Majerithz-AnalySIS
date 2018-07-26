package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.SafeguardsPackage;
import com.empleodigital.murcia.majerithz.repository.SafeguardsPackageRepository;
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
 * REST controller for managing SafeguardsPackage.
 */
@RestController
@RequestMapping("/api")
public class SafeguardsPackageResource {

    private final Logger log = LoggerFactory.getLogger(SafeguardsPackageResource.class);

    private static final String ENTITY_NAME = "safeguardsPackage";

    private final SafeguardsPackageRepository safeguardsPackageRepository;

    public SafeguardsPackageResource(SafeguardsPackageRepository safeguardsPackageRepository) {
        this.safeguardsPackageRepository = safeguardsPackageRepository;
    }

    /**
     * POST  /safeguards-packages : Create a new safeguardsPackage.
     *
     * @param safeguardsPackage the safeguardsPackage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new safeguardsPackage, or with status 400 (Bad Request) if the safeguardsPackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/safeguards-packages")
    @Timed
    public ResponseEntity<SafeguardsPackage> createSafeguardsPackage(@Valid @RequestBody SafeguardsPackage safeguardsPackage) throws URISyntaxException {
        log.debug("REST request to save SafeguardsPackage : {}", safeguardsPackage);
        if (safeguardsPackage.getId() != null) {
            throw new BadRequestAlertException("A new safeguardsPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SafeguardsPackage result = safeguardsPackageRepository.save(safeguardsPackage);
        return ResponseEntity.created(new URI("/api/safeguards-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /safeguards-packages : Updates an existing safeguardsPackage.
     *
     * @param safeguardsPackage the safeguardsPackage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated safeguardsPackage,
     * or with status 400 (Bad Request) if the safeguardsPackage is not valid,
     * or with status 500 (Internal Server Error) if the safeguardsPackage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/safeguards-packages")
    @Timed
    public ResponseEntity<SafeguardsPackage> updateSafeguardsPackage(@Valid @RequestBody SafeguardsPackage safeguardsPackage) throws URISyntaxException {
        log.debug("REST request to update SafeguardsPackage : {}", safeguardsPackage);
        if (safeguardsPackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SafeguardsPackage result = safeguardsPackageRepository.save(safeguardsPackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, safeguardsPackage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /safeguards-packages : get all the safeguardsPackages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of safeguardsPackages in body
     */
    @GetMapping("/safeguards-packages")
    @Timed
    public List<SafeguardsPackage> getAllSafeguardsPackages() {
        log.debug("REST request to get all SafeguardsPackages");
        return safeguardsPackageRepository.findAll();
    }

    /**
     * GET  /safeguards-packages/:id : get the "id" safeguardsPackage.
     *
     * @param id the id of the safeguardsPackage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the safeguardsPackage, or with status 404 (Not Found)
     */
    @GetMapping("/safeguards-packages/{id}")
    @Timed
    public ResponseEntity<SafeguardsPackage> getSafeguardsPackage(@PathVariable Long id) {
        log.debug("REST request to get SafeguardsPackage : {}", id);
        Optional<SafeguardsPackage> safeguardsPackage = safeguardsPackageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(safeguardsPackage);
    }

    /**
     * DELETE  /safeguards-packages/:id : delete the "id" safeguardsPackage.
     *
     * @param id the id of the safeguardsPackage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/safeguards-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteSafeguardsPackage(@PathVariable Long id) {
        log.debug("REST request to delete SafeguardsPackage : {}", id);

        safeguardsPackageRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
