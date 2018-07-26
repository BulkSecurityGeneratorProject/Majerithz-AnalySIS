package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.Analysis;
import com.empleodigital.murcia.majerithz.repository.AnalysisRepository;
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
 * REST controller for managing Analysis.
 */
@RestController
@RequestMapping("/api")
public class AnalysisResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisResource.class);

    private static final String ENTITY_NAME = "analysis";

    private final AnalysisRepository analysisRepository;

    public AnalysisResource(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    /**
     * POST  /analyses : Create a new analysis.
     *
     * @param analysis the analysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analysis, or with status 400 (Bad Request) if the analysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/analyses")
    @Timed
    public ResponseEntity<Analysis> createAnalysis(@Valid @RequestBody Analysis analysis) throws URISyntaxException {
        log.debug("REST request to save Analysis : {}", analysis);
        if (analysis.getId() != null) {
            throw new BadRequestAlertException("A new analysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Analysis result = analysisRepository.save(analysis);
        return ResponseEntity.created(new URI("/api/analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analyses : Updates an existing analysis.
     *
     * @param analysis the analysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analysis,
     * or with status 400 (Bad Request) if the analysis is not valid,
     * or with status 500 (Internal Server Error) if the analysis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/analyses")
    @Timed
    public ResponseEntity<Analysis> updateAnalysis(@Valid @RequestBody Analysis analysis) throws URISyntaxException {
        log.debug("REST request to update Analysis : {}", analysis);
        if (analysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Analysis result = analysisRepository.save(analysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, analysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analyses : get all the analyses.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of analyses in body
     */
    @GetMapping("/analyses")
    @Timed
    public List<Analysis> getAllAnalyses(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Analyses");
        return analysisRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /analyses/:id : get the "id" analysis.
     *
     * @param id the id of the analysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analysis, or with status 404 (Not Found)
     */
    @GetMapping("/analyses/{id}")
    @Timed
    public ResponseEntity<Analysis> getAnalysis(@PathVariable Long id) {
        log.debug("REST request to get Analysis : {}", id);
        Optional<Analysis> analysis = analysisRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(analysis);
    }

    /**
     * DELETE  /analyses/:id : delete the "id" analysis.
     *
     * @param id the id of the analysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/analyses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete Analysis : {}", id);

        analysisRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
