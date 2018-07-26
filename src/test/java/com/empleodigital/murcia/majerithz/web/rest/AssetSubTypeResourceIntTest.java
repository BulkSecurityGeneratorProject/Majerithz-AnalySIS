package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.AssetSubType;
import com.empleodigital.murcia.majerithz.domain.AssetType;
import com.empleodigital.murcia.majerithz.repository.AssetSubTypeRepository;
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
 * Test class for the AssetSubTypeResource REST controller.
 *
 * @see AssetSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class AssetSubTypeResourceIntTest {

    private static final AssetsTypeCode DEFAULT_ASSET_TYPE_CODE = AssetsTypeCode.ESSENTIAL;
    private static final AssetsTypeCode UPDATED_ASSET_TYPE_CODE = AssetsTypeCode.ARCH;

    private static final String DEFAULT_ASSET_SUB_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_SUB_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_SUB_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_SUB_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_SUB_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_SUB_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AssetSubTypeRepository assetSubTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetSubTypeMockMvc;

    private AssetSubType assetSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetSubTypeResource assetSubTypeResource = new AssetSubTypeResource(assetSubTypeRepository);
        this.restAssetSubTypeMockMvc = MockMvcBuilders.standaloneSetup(assetSubTypeResource)
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
    public static AssetSubType createEntity(EntityManager em) {
        AssetSubType assetSubType = new AssetSubType()
            .assetTypeCode(DEFAULT_ASSET_TYPE_CODE)
            .assetSubTypeCode(DEFAULT_ASSET_SUB_TYPE_CODE)
            .assetSubTypeName(DEFAULT_ASSET_SUB_TYPE_NAME)
            .assetSubTypeDescription(DEFAULT_ASSET_SUB_TYPE_DESCRIPTION);
        // Add required entity
        AssetType assetType = AssetTypeResourceIntTest.createEntity(em);
        em.persist(assetType);
        em.flush();
        assetSubType.setAssetType(assetType);
        return assetSubType;
    }

    @Before
    public void initTest() {
        assetSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetSubType() throws Exception {
        int databaseSizeBeforeCreate = assetSubTypeRepository.findAll().size();

        // Create the AssetSubType
        restAssetSubTypeMockMvc.perform(post("/api/asset-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSubType)))
            .andExpect(status().isCreated());

        // Validate the AssetSubType in the database
        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AssetSubType testAssetSubType = assetSubTypeList.get(assetSubTypeList.size() - 1);
        assertThat(testAssetSubType.getAssetTypeCode()).isEqualTo(DEFAULT_ASSET_TYPE_CODE);
        assertThat(testAssetSubType.getAssetSubTypeCode()).isEqualTo(DEFAULT_ASSET_SUB_TYPE_CODE);
        assertThat(testAssetSubType.getAssetSubTypeName()).isEqualTo(DEFAULT_ASSET_SUB_TYPE_NAME);
        assertThat(testAssetSubType.getAssetSubTypeDescription()).isEqualTo(DEFAULT_ASSET_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAssetSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetSubTypeRepository.findAll().size();

        // Create the AssetSubType with an existing ID
        assetSubType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetSubTypeMockMvc.perform(post("/api/asset-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSubType)))
            .andExpect(status().isBadRequest());

        // Validate the AssetSubType in the database
        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssetSubTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetSubTypeRepository.findAll().size();
        // set the field null
        assetSubType.setAssetSubTypeName(null);

        // Create the AssetSubType, which fails.

        restAssetSubTypeMockMvc.perform(post("/api/asset-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSubType)))
            .andExpect(status().isBadRequest());

        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetSubTypes() throws Exception {
        // Initialize the database
        assetSubTypeRepository.saveAndFlush(assetSubType);

        // Get all the assetSubTypeList
        restAssetSubTypeMockMvc.perform(get("/api/asset-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetTypeCode").value(hasItem(DEFAULT_ASSET_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].assetSubTypeCode").value(hasItem(DEFAULT_ASSET_SUB_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].assetSubTypeName").value(hasItem(DEFAULT_ASSET_SUB_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].assetSubTypeDescription").value(hasItem(DEFAULT_ASSET_SUB_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getAssetSubType() throws Exception {
        // Initialize the database
        assetSubTypeRepository.saveAndFlush(assetSubType);

        // Get the assetSubType
        restAssetSubTypeMockMvc.perform(get("/api/asset-sub-types/{id}", assetSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetSubType.getId().intValue()))
            .andExpect(jsonPath("$.assetTypeCode").value(DEFAULT_ASSET_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.assetSubTypeCode").value(DEFAULT_ASSET_SUB_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.assetSubTypeName").value(DEFAULT_ASSET_SUB_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.assetSubTypeDescription").value(DEFAULT_ASSET_SUB_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAssetSubType() throws Exception {
        // Get the assetSubType
        restAssetSubTypeMockMvc.perform(get("/api/asset-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetSubType() throws Exception {
        // Initialize the database
        assetSubTypeRepository.saveAndFlush(assetSubType);

        int databaseSizeBeforeUpdate = assetSubTypeRepository.findAll().size();

        // Update the assetSubType
        AssetSubType updatedAssetSubType = assetSubTypeRepository.findById(assetSubType.getId()).get();
        // Disconnect from session so that the updates on updatedAssetSubType are not directly saved in db
        em.detach(updatedAssetSubType);
        updatedAssetSubType
            .assetTypeCode(UPDATED_ASSET_TYPE_CODE)
            .assetSubTypeCode(UPDATED_ASSET_SUB_TYPE_CODE)
            .assetSubTypeName(UPDATED_ASSET_SUB_TYPE_NAME)
            .assetSubTypeDescription(UPDATED_ASSET_SUB_TYPE_DESCRIPTION);

        restAssetSubTypeMockMvc.perform(put("/api/asset-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetSubType)))
            .andExpect(status().isOk());

        // Validate the AssetSubType in the database
        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeUpdate);
        AssetSubType testAssetSubType = assetSubTypeList.get(assetSubTypeList.size() - 1);
        assertThat(testAssetSubType.getAssetTypeCode()).isEqualTo(UPDATED_ASSET_TYPE_CODE);
        assertThat(testAssetSubType.getAssetSubTypeCode()).isEqualTo(UPDATED_ASSET_SUB_TYPE_CODE);
        assertThat(testAssetSubType.getAssetSubTypeName()).isEqualTo(UPDATED_ASSET_SUB_TYPE_NAME);
        assertThat(testAssetSubType.getAssetSubTypeDescription()).isEqualTo(UPDATED_ASSET_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetSubType() throws Exception {
        int databaseSizeBeforeUpdate = assetSubTypeRepository.findAll().size();

        // Create the AssetSubType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetSubTypeMockMvc.perform(put("/api/asset-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetSubType)))
            .andExpect(status().isBadRequest());

        // Validate the AssetSubType in the database
        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssetSubType() throws Exception {
        // Initialize the database
        assetSubTypeRepository.saveAndFlush(assetSubType);

        int databaseSizeBeforeDelete = assetSubTypeRepository.findAll().size();

        // Get the assetSubType
        restAssetSubTypeMockMvc.perform(delete("/api/asset-sub-types/{id}", assetSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssetSubType> assetSubTypeList = assetSubTypeRepository.findAll();
        assertThat(assetSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetSubType.class);
        AssetSubType assetSubType1 = new AssetSubType();
        assetSubType1.setId(1L);
        AssetSubType assetSubType2 = new AssetSubType();
        assetSubType2.setId(assetSubType1.getId());
        assertThat(assetSubType1).isEqualTo(assetSubType2);
        assetSubType2.setId(2L);
        assertThat(assetSubType1).isNotEqualTo(assetSubType2);
        assetSubType1.setId(null);
        assertThat(assetSubType1).isNotEqualTo(assetSubType2);
    }
}
