package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.Asset;
import com.empleodigital.murcia.majerithz.domain.AssetSubType;
import com.empleodigital.murcia.majerithz.domain.ExistingSafeguards;
import com.empleodigital.murcia.majerithz.domain.Threat;
import com.empleodigital.murcia.majerithz.domain.Company;
import com.empleodigital.murcia.majerithz.repository.AssetRepository;
import com.empleodigital.murcia.majerithz.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.empleodigital.murcia.majerithz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.empleodigital.murcia.majerithz.domain.enumeration.AssetsTypeCode;
/**
 * Test class for the AssetResource REST controller.
 *
 * @see AssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class AssetResourceIntTest {

    private static final AssetsTypeCode DEFAULT_ASSET_TYPE_CODE = AssetsTypeCode.ESSENTIAL;
    private static final AssetsTypeCode UPDATED_ASSET_TYPE_CODE = AssetsTypeCode.ARCH;

    private static final String DEFAULT_ASSET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_ASSET = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_ASSET = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_ASSET_QUALITATIVE_VALUE = 0D;
    private static final Double UPDATED_ASSET_QUALITATIVE_VALUE = 1D;

    private static final Double DEFAULT_ASSET_QUANTITATIVE_VALUE = 1D;
    private static final Double UPDATED_ASSET_QUANTITATIVE_VALUE = 2D;

    private static final Double DEFAULT_POTENTIAL_RISK = 0D;
    private static final Double UPDATED_POTENTIAL_RISK = 1D;

    private static final Double DEFAULT_ESTIMATED_RISK = 0D;
    private static final Double UPDATED_ESTIMATED_RISK = 1D;

    @Autowired
    private AssetRepository assetRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetMockMvc;

    private Asset asset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetResource assetResource = new AssetResource(assetRepository);
        this.restAssetMockMvc = MockMvcBuilders.standaloneSetup(assetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .assetTypeCode(DEFAULT_ASSET_TYPE_CODE)
            .assetName(DEFAULT_ASSET_NAME)
            .descriptionAsset(DEFAULT_DESCRIPTION_ASSET)
            .identifier(DEFAULT_IDENTIFIER)
            .assetLocation(DEFAULT_ASSET_LOCATION)
            .owner(DEFAULT_OWNER)
            .responsible(DEFAULT_RESPONSIBLE)
            .quantity(DEFAULT_QUANTITY)
            .assetQualitativeValue(DEFAULT_ASSET_QUALITATIVE_VALUE)
            .assetQuantitativeValue(DEFAULT_ASSET_QUANTITATIVE_VALUE)
            .potentialRisk(DEFAULT_POTENTIAL_RISK)
            .estimatedRisk(DEFAULT_ESTIMATED_RISK);
        // Add required entity
        AssetSubType assetSubType = AssetSubTypeResourceIntTest.createEntity(em);
        em.persist(assetSubType);
        em.flush();
        asset.getAssetSubTypes().add(assetSubType);
        // Add required entity
        ExistingSafeguards existingSafeguards = ExistingSafeguardsResourceIntTest.createEntity(em);
        em.persist(existingSafeguards);
        em.flush();
        asset.getExistingSafeguards().add(existingSafeguards);
        // Add required entity
        Threat threat = ThreatResourceIntTest.createEntity(em);
        em.persist(threat);
        em.flush();
        asset.getThreats().add(threat);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        asset.setCompany(company);
        return asset;
    }

    @Before
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getAssetTypeCode()).isEqualTo(DEFAULT_ASSET_TYPE_CODE);
        assertThat(testAsset.getAssetName()).isEqualTo(DEFAULT_ASSET_NAME);
        assertThat(testAsset.getDescriptionAsset()).isEqualTo(DEFAULT_DESCRIPTION_ASSET);
        assertThat(testAsset.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testAsset.getAssetLocation()).isEqualTo(DEFAULT_ASSET_LOCATION);
        assertThat(testAsset.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testAsset.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
        assertThat(testAsset.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAsset.getAssetQualitativeValue()).isEqualTo(DEFAULT_ASSET_QUALITATIVE_VALUE);
        assertThat(testAsset.getAssetQuantitativeValue()).isEqualTo(DEFAULT_ASSET_QUANTITATIVE_VALUE);
        assertThat(testAsset.getPotentialRisk()).isEqualTo(DEFAULT_POTENTIAL_RISK);
        assertThat(testAsset.getEstimatedRisk()).isEqualTo(DEFAULT_ESTIMATED_RISK);
    }

    @Test
    @Transactional
    public void createAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset with an existing ID
        asset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setAssetName(null);

        // Create the Asset, which fails.

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setIdentifier(null);

        // Create the Asset, which fails.

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetTypeCode").value(hasItem(DEFAULT_ASSET_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].assetName").value(hasItem(DEFAULT_ASSET_NAME.toString())))
            .andExpect(jsonPath("$.[*].descriptionAsset").value(hasItem(DEFAULT_DESCRIPTION_ASSET.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].assetLocation").value(hasItem(DEFAULT_ASSET_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].assetQualitativeValue").value(hasItem(DEFAULT_ASSET_QUALITATIVE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].assetQuantitativeValue").value(hasItem(DEFAULT_ASSET_QUANTITATIVE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].potentialRisk").value(hasItem(DEFAULT_POTENTIAL_RISK.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatedRisk").value(hasItem(DEFAULT_ESTIMATED_RISK.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.assetTypeCode").value(DEFAULT_ASSET_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.assetName").value(DEFAULT_ASSET_NAME.toString()))
            .andExpect(jsonPath("$.descriptionAsset").value(DEFAULT_DESCRIPTION_ASSET.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.assetLocation").value(DEFAULT_ASSET_LOCATION.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.assetQualitativeValue").value(DEFAULT_ASSET_QUALITATIVE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.assetQuantitativeValue").value(DEFAULT_ASSET_QUANTITATIVE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.potentialRisk").value(DEFAULT_POTENTIAL_RISK.doubleValue()))
            .andExpect(jsonPath("$.estimatedRisk").value(DEFAULT_ESTIMATED_RISK.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findById(asset.getId()).get();
        // Disconnect from session so that the updates on updatedAsset are not directly saved in db
        em.detach(updatedAsset);
        updatedAsset
            .assetTypeCode(UPDATED_ASSET_TYPE_CODE)
            .assetName(UPDATED_ASSET_NAME)
            .descriptionAsset(UPDATED_DESCRIPTION_ASSET)
            .identifier(UPDATED_IDENTIFIER)
            .assetLocation(UPDATED_ASSET_LOCATION)
            .owner(UPDATED_OWNER)
            .responsible(UPDATED_RESPONSIBLE)
            .quantity(UPDATED_QUANTITY)
            .assetQualitativeValue(UPDATED_ASSET_QUALITATIVE_VALUE)
            .assetQuantitativeValue(UPDATED_ASSET_QUANTITATIVE_VALUE)
            .potentialRisk(UPDATED_POTENTIAL_RISK)
            .estimatedRisk(UPDATED_ESTIMATED_RISK);

        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsset)))
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getAssetTypeCode()).isEqualTo(UPDATED_ASSET_TYPE_CODE);
        assertThat(testAsset.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAsset.getDescriptionAsset()).isEqualTo(UPDATED_DESCRIPTION_ASSET);
        assertThat(testAsset.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testAsset.getAssetLocation()).isEqualTo(UPDATED_ASSET_LOCATION);
        assertThat(testAsset.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testAsset.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testAsset.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAsset.getAssetQualitativeValue()).isEqualTo(UPDATED_ASSET_QUALITATIVE_VALUE);
        assertThat(testAsset.getAssetQuantitativeValue()).isEqualTo(UPDATED_ASSET_QUANTITATIVE_VALUE);
        assertThat(testAsset.getPotentialRisk()).isEqualTo(UPDATED_POTENTIAL_RISK);
        assertThat(testAsset.getEstimatedRisk()).isEqualTo(UPDATED_ESTIMATED_RISK);
    }

    @Test
    @Transactional
    public void updateNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Create the Asset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Get the asset
        restAssetMockMvc.perform(delete("/api/assets/{id}", asset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = new Asset();
        asset1.setId(1L);
        Asset asset2 = new Asset();
        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);
        asset2.setId(2L);
        assertThat(asset1).isNotEqualTo(asset2);
        asset1.setId(null);
        assertThat(asset1).isNotEqualTo(asset2);
    }
}
