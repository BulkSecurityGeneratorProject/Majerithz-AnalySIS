package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.SafeguardType;
import com.empleodigital.murcia.majerithz.repository.SafeguardTypeRepository;
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

import com.empleodigital.murcia.majerithz.domain.enumeration.SafeguardsTypeCode;
/**
 * Test class for the SafeguardTypeResource REST controller.
 *
 * @see SafeguardTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class SafeguardTypeResourceIntTest {

    private static final SafeguardsTypeCode DEFAULT_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.H;
    private static final SafeguardsTypeCode UPDATED_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.D;

    private static final String DEFAULT_SAFEGUARD_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SAFEGUARD_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SafeguardTypeRepository safeguardTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSafeguardTypeMockMvc;

    private SafeguardType safeguardType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SafeguardTypeResource safeguardTypeResource = new SafeguardTypeResource(safeguardTypeRepository);
        this.restSafeguardTypeMockMvc = MockMvcBuilders.standaloneSetup(safeguardTypeResource)
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
    public static SafeguardType createEntity(EntityManager em) {
        SafeguardType safeguardType = new SafeguardType()
            .safeguardsTypeCode(DEFAULT_SAFEGUARDS_TYPE_CODE)
            .safeguardTypeName(DEFAULT_SAFEGUARD_TYPE_NAME)
            .safeguardTypeDescription(DEFAULT_SAFEGUARD_TYPE_DESCRIPTION);
        return safeguardType;
    }

    @Before
    public void initTest() {
        safeguardType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeguardType() throws Exception {
        int databaseSizeBeforeCreate = safeguardTypeRepository.findAll().size();

        // Create the SafeguardType
        restSafeguardTypeMockMvc.perform(post("/api/safeguard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardType)))
            .andExpect(status().isCreated());

        // Validate the SafeguardType in the database
        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SafeguardType testSafeguardType = safeguardTypeList.get(safeguardTypeList.size() - 1);
        assertThat(testSafeguardType.getSafeguardsTypeCode()).isEqualTo(DEFAULT_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguardType.getSafeguardTypeName()).isEqualTo(DEFAULT_SAFEGUARD_TYPE_NAME);
        assertThat(testSafeguardType.getSafeguardTypeDescription()).isEqualTo(DEFAULT_SAFEGUARD_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSafeguardTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeguardTypeRepository.findAll().size();

        // Create the SafeguardType with an existing ID
        safeguardType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeguardTypeMockMvc.perform(post("/api/safeguard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardType)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardType in the database
        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSafeguardTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeguardTypeRepository.findAll().size();
        // set the field null
        safeguardType.setSafeguardTypeName(null);

        // Create the SafeguardType, which fails.

        restSafeguardTypeMockMvc.perform(post("/api/safeguard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardType)))
            .andExpect(status().isBadRequest());

        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSafeguardTypes() throws Exception {
        // Initialize the database
        safeguardTypeRepository.saveAndFlush(safeguardType);

        // Get all the safeguardTypeList
        restSafeguardTypeMockMvc.perform(get("/api/safeguard-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeguardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeguardsTypeCode").value(hasItem(DEFAULT_SAFEGUARDS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].safeguardTypeName").value(hasItem(DEFAULT_SAFEGUARD_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].safeguardTypeDescription").value(hasItem(DEFAULT_SAFEGUARD_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getSafeguardType() throws Exception {
        // Initialize the database
        safeguardTypeRepository.saveAndFlush(safeguardType);

        // Get the safeguardType
        restSafeguardTypeMockMvc.perform(get("/api/safeguard-types/{id}", safeguardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(safeguardType.getId().intValue()))
            .andExpect(jsonPath("$.safeguardsTypeCode").value(DEFAULT_SAFEGUARDS_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.safeguardTypeName").value(DEFAULT_SAFEGUARD_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.safeguardTypeDescription").value(DEFAULT_SAFEGUARD_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSafeguardType() throws Exception {
        // Get the safeguardType
        restSafeguardTypeMockMvc.perform(get("/api/safeguard-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeguardType() throws Exception {
        // Initialize the database
        safeguardTypeRepository.saveAndFlush(safeguardType);

        int databaseSizeBeforeUpdate = safeguardTypeRepository.findAll().size();

        // Update the safeguardType
        SafeguardType updatedSafeguardType = safeguardTypeRepository.findById(safeguardType.getId()).get();
        // Disconnect from session so that the updates on updatedSafeguardType are not directly saved in db
        em.detach(updatedSafeguardType);
        updatedSafeguardType
            .safeguardsTypeCode(UPDATED_SAFEGUARDS_TYPE_CODE)
            .safeguardTypeName(UPDATED_SAFEGUARD_TYPE_NAME)
            .safeguardTypeDescription(UPDATED_SAFEGUARD_TYPE_DESCRIPTION);

        restSafeguardTypeMockMvc.perform(put("/api/safeguard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSafeguardType)))
            .andExpect(status().isOk());

        // Validate the SafeguardType in the database
        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeUpdate);
        SafeguardType testSafeguardType = safeguardTypeList.get(safeguardTypeList.size() - 1);
        assertThat(testSafeguardType.getSafeguardsTypeCode()).isEqualTo(UPDATED_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguardType.getSafeguardTypeName()).isEqualTo(UPDATED_SAFEGUARD_TYPE_NAME);
        assertThat(testSafeguardType.getSafeguardTypeDescription()).isEqualTo(UPDATED_SAFEGUARD_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeguardType() throws Exception {
        int databaseSizeBeforeUpdate = safeguardTypeRepository.findAll().size();

        // Create the SafeguardType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSafeguardTypeMockMvc.perform(put("/api/safeguard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardType)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardType in the database
        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeguardType() throws Exception {
        // Initialize the database
        safeguardTypeRepository.saveAndFlush(safeguardType);

        int databaseSizeBeforeDelete = safeguardTypeRepository.findAll().size();

        // Get the safeguardType
        restSafeguardTypeMockMvc.perform(delete("/api/safeguard-types/{id}", safeguardType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SafeguardType> safeguardTypeList = safeguardTypeRepository.findAll();
        assertThat(safeguardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeguardType.class);
        SafeguardType safeguardType1 = new SafeguardType();
        safeguardType1.setId(1L);
        SafeguardType safeguardType2 = new SafeguardType();
        safeguardType2.setId(safeguardType1.getId());
        assertThat(safeguardType1).isEqualTo(safeguardType2);
        safeguardType2.setId(2L);
        assertThat(safeguardType1).isNotEqualTo(safeguardType2);
        safeguardType1.setId(null);
        assertThat(safeguardType1).isNotEqualTo(safeguardType2);
    }
}
