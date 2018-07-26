package com.empleodigital.murcia.majerithz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empleodigital.murcia.majerithz.domain.AssetType;
import com.empleodigital.murcia.majerithz.repository.AssetTypeRepository;
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
 * REST controller for managing AssetType.
 */
@RestController
@RequestMapping("/api")
public class AssetTypeResource {

    private final Logger log = LoggerFactory.getLogger(AssetTypeResource.class);

    private static final String ENTITY_NAME = "assetType";

    private final AssetTypeRepository assetTypeRepository;

    public AssetTypeResource(AssetTypeRepository assetTypeRepository) {
        this.assetTypeRepository = assetTypeRepository;
    }

    /**
     * POST  /asset-types : Create a new assetType.
     *
     * @param assetType the assetType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetType, or with status 400 (Bad Request) if the assetType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asset-types")
    @Timed
    public ResponseEntity<AssetType> createAssetType(@Valid @RequestBody AssetType assetType) throws URISyntaxException {
        log.debug("REST request to save AssetType : {}", assetType);
        if (assetType.getId() != null) {
            throw new BadRequestAlertException("A new assetType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetType result = assetTypeRepository.save(assetType);
        return ResponseEntity.created(new URI("/api/asset-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asset-types : Updates an existing assetType.
     *
     * @param assetType the assetType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetType,
     * or with status 400 (Bad Request) if the assetType is not valid,
     * or with status 500 (Internal Server Error) if the assetType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asset-types")
    @Timed
    public ResponseEntity<AssetType> updateAssetType(@Valid @RequestBody AssetType assetType) throws URISyntaxException {
        log.debug("REST request to update AssetType : {}", assetType);
        if (assetType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetType result = assetTypeRepository.save(assetType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asset-types : get all the assetTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assetTypes in body
     */
    @GetMapping("/asset-types")
    @Timed
    public List<AssetType> getAllAssetTypes() {
        log.debug("REST request to get all AssetTypes");
        return assetTypeRepository.findAll();
    }

    /**
     * GET  /asset-types/:id : get the "id" assetType.
     *
     * @param id the id of the assetType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetType, or with status 404 (Not Found)
     */
    @GetMapping("/asset-types/{id}")
    @Timed
    public ResponseEntity<AssetType> getAssetType(@PathVariable Long id) {
        log.debug("REST request to get AssetType : {}", id);
        Optional<AssetType> assetType = assetTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(assetType);
    }

    /**
     * DELETE  /asset-types/:id : delete the "id" assetType.
     *
     * @param id the id of the assetType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asset-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetType(@PathVariable Long id) {
        log.debug("REST request to delete AssetType : {}", id);

        assetTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
