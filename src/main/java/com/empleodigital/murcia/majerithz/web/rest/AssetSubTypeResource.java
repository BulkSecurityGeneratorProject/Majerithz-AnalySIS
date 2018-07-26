package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.AssetSubType;
import com.empleodigital.murcia.majerithz.repository.AssetSubTypeRepository;
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
 * REST controller for managing AssetSubType.
 */
@RestController
@RequestMapping("/api")
public class AssetSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(AssetSubTypeResource.class);

    private static final String ENTITY_NAME = "assetSubType";

    private final AssetSubTypeRepository assetSubTypeRepository;

    public AssetSubTypeResource(AssetSubTypeRepository assetSubTypeRepository) {
        this.assetSubTypeRepository = assetSubTypeRepository;
    }

    /**
     * POST  /asset-sub-types : Create a new assetSubType.
     *
     * @param assetSubType the assetSubType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetSubType, or with status 400 (Bad Request) if the assetSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asset-sub-types")
    @Timed
    public ResponseEntity<AssetSubType> createAssetSubType(@Valid @RequestBody AssetSubType assetSubType) throws URISyntaxException {
        log.debug("REST request to save AssetSubType : {}", assetSubType);
        if (assetSubType.getId() != null) {
            throw new BadRequestAlertException("A new assetSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetSubType result = assetSubTypeRepository.save(assetSubType);
        return ResponseEntity.created(new URI("/api/asset-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset-sub-types : Updates an existing assetSubType.
     *
     * @param assetSubType the assetSubType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetSubType,
     * or with status 400 (Bad Request) if the assetSubType is not valid,
     * or with status 500 (Internal Server Error) if the assetSubType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asset-sub-types")
    @Timed
    public ResponseEntity<AssetSubType> updateAssetSubType(@Valid @RequestBody AssetSubType assetSubType) throws URISyntaxException {
        log.debug("REST request to update AssetSubType : {}", assetSubType);
        if (assetSubType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetSubType result = assetSubTypeRepository.save(assetSubType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetSubType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset-sub-types : get all the assetSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetSubTypes in body
     */
    @GetMapping("/asset-sub-types")
    @Timed
    public List<AssetSubType> getAllAssetSubTypes() {
        log.debug("REST request to get all AssetSubTypes");
        return assetSubTypeRepository.findAll();
    }

    /**
     * GET  /asset-sub-types/:id : get the "id" assetSubType.
     *
     * @param id the id of the assetSubType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetSubType, or with status 404 (Not Found)
     */
    @GetMapping("/asset-sub-types/{id}")
    @Timed
    public ResponseEntity<AssetSubType> getAssetSubType(@PathVariable Long id) {
        log.debug("REST request to get AssetSubType : {}", id);
        Optional<AssetSubType> assetSubType = assetSubTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(assetSubType);
    }

    /**
     * DELETE  /asset-sub-types/:id : delete the "id" assetSubType.
     *
     * @param id the id of the assetSubType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asset-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetSubType(@PathVariable Long id) {
        log.debug("REST request to delete AssetSubType : {}", id);

        assetSubTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
