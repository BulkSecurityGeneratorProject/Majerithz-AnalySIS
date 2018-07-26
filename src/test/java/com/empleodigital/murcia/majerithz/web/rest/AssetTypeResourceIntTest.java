package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.AssetType;
import com.empleodigital.murcia.majerithz.domain.AssetSubType;
import com.empleodigital.murcia.majerithz.repository.AssetTypeRepository;
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
 * Test class for the AssetTypeResource REST controller.
 *
 * @see AssetTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class AssetTypeResourceIntTest {

    private static final AssetsTypeCode DEFAULT_ASSET_TYPE_CODE = AssetsTypeCode.ESSENTIAL;
    private static final AssetsTypeCode UPDATED_ASSET_TYPE_CODE = AssetsTypeCode.ARCH;

    private static final String DEFAULT_ASSET_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AssetTypeRepository assetTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetTypeMockMvc;

    private AssetType assetType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetTypeResource assetTypeResource = new AssetTypeResource(assetTypeRepository);
        this.restAssetTypeMockMvc = MockMvcBuilders.standaloneSetup(assetTypeResource)
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
    public static AssetType createEntity(EntityManager em) {
        AssetType assetType = new AssetType()
            .assetTypeCode(DEFAULT_ASSET_TYPE_CODE)
            .assetTypeName(DEFAULT_ASSET_TYPE_NAME)
            .assetTypeDescription(DEFAULT_ASSET_TYPE_DESCRIPTION);
        // Add required entity
        AssetSubType assetSubType = AssetSubTypeResourceIntTest.createEntity(em);
        em.persist(assetSubType);
        em.flush();
        assetType.getAssetSubTypes().add(assetSubType);
        return assetType;
    }

    @Before
    public void initTest() {
        assetType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetType() throws Exception {
        int databaseSizeBeforeCreate = assetTypeRepository.findAll().size();

        // Create the AssetType
        restAssetTypeMockMvc.perform(post("/api/asset-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetType)))
            .andExpect(status().isCreated());

        // Validate the AssetType in the database
        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AssetType testAssetType = assetTypeList.get(assetTypeList.size() - 1);
        assertThat(testAssetType.getAssetTypeCode()).isEqualTo(DEFAULT_ASSET_TYPE_CODE);
        assertThat(testAssetType.getAssetTypeName()).isEqualTo(DEFAULT_ASSET_TYPE_NAME);
        assertThat(testAssetType.getAssetTypeDescription()).isEqualTo(DEFAULT_ASSET_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAssetTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetTypeRepository.findAll().size();

        // Create the AssetType with an existing ID
        assetType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetTypeMockMvc.perform(post("/api/asset-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetType)))
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssetTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetTypeRepository.findAll().size();
        // set the field null
        assetType.setAssetTypeName(null);

        // Create the AssetType, which fails.

        restAssetTypeMockMvc.perform(post("/api/asset-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetType)))
            .andExpect(status().isBadRequest());

        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetTypes() throws Exception {
        // Initialize the database
        assetTypeRepository.saveAndFlush(assetType);

        // Get all the assetTypeList
        restAssetTypeMockMvc.perform(get("/api/asset-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetType.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetTypeCode").value(hasItem(DEFAULT_ASSET_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].assetTypeName").value(hasItem(DEFAULT_ASSET_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].assetTypeDescription").value(hasItem(DEFAULT_ASSET_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getAssetType() throws Exception {
        // Initialize the database
        assetTypeRepository.saveAndFlush(assetType);

        // Get the assetType
        restAssetTypeMockMvc.perform(get("/api/asset-types/{id}", assetType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetType.getId().intValue()))
            .andExpect(jsonPath("$.assetTypeCode").value(DEFAULT_ASSET_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.assetTypeName").value(DEFAULT_ASSET_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.assetTypeDescription").value(DEFAULT_ASSET_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAssetType() throws Exception {
        // Get the assetType
        restAssetTypeMockMvc.perform(get("/api/asset-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetType() throws Exception {
        // Initialize the database
        assetTypeRepository.saveAndFlush(assetType);

        int databaseSizeBeforeUpdate = assetTypeRepository.findAll().size();

        // Update the assetType
        AssetType updatedAssetType = assetTypeRepository.findById(assetType.getId()).get();
        // Disconnect from session so that the updates on updatedAssetType are not directly saved in db
        em.detach(updatedAssetType);
        updatedAssetType
            .assetTypeCode(UPDATED_ASSET_TYPE_CODE)
            .assetTypeName(UPDATED_ASSET_TYPE_NAME)
            .assetTypeDescription(UPDATED_ASSET_TYPE_DESCRIPTION);

        restAssetTypeMockMvc.perform(put("/api/asset-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetType)))
            .andExpect(status().isOk());

        // Validate the AssetType in the database
        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeUpdate);
        AssetType testAssetType = assetTypeList.get(assetTypeList.size() - 1);
        assertThat(testAssetType.getAssetTypeCode()).isEqualTo(UPDATED_ASSET_TYPE_CODE);
        assertThat(testAssetType.getAssetTypeName()).isEqualTo(UPDATED_ASSET_TYPE_NAME);
        assertThat(testAssetType.getAssetTypeDescription()).isEqualTo(UPDATED_ASSET_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetType() throws Exception {
        int databaseSizeBeforeUpdate = assetTypeRepository.findAll().size();

        // Create the AssetType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetTypeMockMvc.perform(put("/api/asset-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetType)))
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssetType() throws Exception {
        // Initialize the database
        assetTypeRepository.saveAndFlush(assetType);

        int databaseSizeBeforeDelete = assetTypeRepository.findAll().size();

        // Get the assetType
        restAssetTypeMockMvc.perform(delete("/api/asset-types/{id}", assetType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetType> assetTypeList = assetTypeRepository.findAll();
        assertThat(assetTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetType.class);
        AssetType assetType1 = new AssetType();
        assetType1.setId(1L);
        AssetType assetType2 = new AssetType();
        assetType2.setId(assetType1.getId());
        assertThat(assetType1).isEqualTo(assetType2);
        assetType2.setId(2L);
        assertThat(assetType1).isNotEqualTo(assetType2);
        assetType1.setId(null);
        assertThat(assetType1).isNotEqualTo(assetType2);
    }
}
