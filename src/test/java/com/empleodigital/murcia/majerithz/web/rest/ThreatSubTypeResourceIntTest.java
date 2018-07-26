package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.ThreatSubType;
import com.empleodigital.murcia.majerithz.repository.ThreatSubTypeRepository;
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

import com.empleodigital.murcia.majerithz.domain.enumeration.ThreatsTypeCode;
/**
 * Test class for the ThreatSubTypeResource REST controller.
 *
 * @see ThreatSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class ThreatSubTypeResourceIntTest {

    private static final ThreatsTypeCode DEFAULT_THREAT_TYPE_CODE = ThreatsTypeCode.N;
    private static final ThreatsTypeCode UPDATED_THREAT_TYPE_CODE = ThreatsTypeCode.I;

    private static final String DEFAULT_THREAT_SUB_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_SUB_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_THREAT_SUB_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_SUB_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THREAT_SUB_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_SUB_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ThreatSubTypeRepository threatSubTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThreatSubTypeMockMvc;

    private ThreatSubType threatSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThreatSubTypeResource threatSubTypeResource = new ThreatSubTypeResource(threatSubTypeRepository);
        this.restThreatSubTypeMockMvc = MockMvcBuilders.standaloneSetup(threatSubTypeResource)
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
    public static ThreatSubType createEntity(EntityManager em) {
        ThreatSubType threatSubType = new ThreatSubType()
            .threatTypeCode(DEFAULT_THREAT_TYPE_CODE)
            .threatSubTypeCode(DEFAULT_THREAT_SUB_TYPE_CODE)
            .threatSubTypeName(DEFAULT_THREAT_SUB_TYPE_NAME)
            .threatSubTypeDescription(DEFAULT_THREAT_SUB_TYPE_DESCRIPTION);
        return threatSubType;
    }

    @Before
    public void initTest() {
        threatSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createThreatSubType() throws Exception {
        int databaseSizeBeforeCreate = threatSubTypeRepository.findAll().size();

        // Create the ThreatSubType
        restThreatSubTypeMockMvc.perform(post("/api/threat-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatSubType)))
            .andExpect(status().isCreated());

        // Validate the ThreatSubType in the database
        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ThreatSubType testThreatSubType = threatSubTypeList.get(threatSubTypeList.size() - 1);
        assertThat(testThreatSubType.getThreatTypeCode()).isEqualTo(DEFAULT_THREAT_TYPE_CODE);
        assertThat(testThreatSubType.getThreatSubTypeCode()).isEqualTo(DEFAULT_THREAT_SUB_TYPE_CODE);
        assertThat(testThreatSubType.getThreatSubTypeName()).isEqualTo(DEFAULT_THREAT_SUB_TYPE_NAME);
        assertThat(testThreatSubType.getThreatSubTypeDescription()).isEqualTo(DEFAULT_THREAT_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createThreatSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = threatSubTypeRepository.findAll().size();

        // Create the ThreatSubType with an existing ID
        threatSubType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThreatSubTypeMockMvc.perform(post("/api/threat-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatSubType)))
            .andExpect(status().isBadRequest());

        // Validate the ThreatSubType in the database
        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThreatSubTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = threatSubTypeRepository.findAll().size();
        // set the field null
        threatSubType.setThreatSubTypeName(null);

        // Create the ThreatSubType, which fails.

        restThreatSubTypeMockMvc.perform(post("/api/threat-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatSubType)))
            .andExpect(status().isBadRequest());

        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThreatSubTypes() throws Exception {
        // Initialize the database
        threatSubTypeRepository.saveAndFlush(threatSubType);

        // Get all the threatSubTypeList
        restThreatSubTypeMockMvc.perform(get("/api/threat-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(threatSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].threatTypeCode").value(hasItem(DEFAULT_THREAT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].threatSubTypeCode").value(hasItem(DEFAULT_THREAT_SUB_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].threatSubTypeName").value(hasItem(DEFAULT_THREAT_SUB_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].threatSubTypeDescription").value(hasItem(DEFAULT_THREAT_SUB_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getThreatSubType() throws Exception {
        // Initialize the database
        threatSubTypeRepository.saveAndFlush(threatSubType);

        // Get the threatSubType
        restThreatSubTypeMockMvc.perform(get("/api/threat-sub-types/{id}", threatSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(threatSubType.getId().intValue()))
            .andExpect(jsonPath("$.threatTypeCode").value(DEFAULT_THREAT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.threatSubTypeCode").value(DEFAULT_THREAT_SUB_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.threatSubTypeName").value(DEFAULT_THREAT_SUB_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.threatSubTypeDescription").value(DEFAULT_THREAT_SUB_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingThreatSubType() throws Exception {
        // Get the threatSubType
        restThreatSubTypeMockMvc.perform(get("/api/threat-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThreatSubType() throws Exception {
        // Initialize the database
        threatSubTypeRepository.saveAndFlush(threatSubType);

        int databaseSizeBeforeUpdate = threatSubTypeRepository.findAll().size();

        // Update the threatSubType
        ThreatSubType updatedThreatSubType = threatSubTypeRepository.findById(threatSubType.getId()).get();
        // Disconnect from session so that the updates on updatedThreatSubType are not directly saved in db
        em.detach(updatedThreatSubType);
        updatedThreatSubType
            .threatTypeCode(UPDATED_THREAT_TYPE_CODE)
            .threatSubTypeCode(UPDATED_THREAT_SUB_TYPE_CODE)
            .threatSubTypeName(UPDATED_THREAT_SUB_TYPE_NAME)
            .threatSubTypeDescription(UPDATED_THREAT_SUB_TYPE_DESCRIPTION);

        restThreatSubTypeMockMvc.perform(put("/api/threat-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThreatSubType)))
            .andExpect(status().isOk());

        // Validate the ThreatSubType in the database
        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeUpdate);
        ThreatSubType testThreatSubType = threatSubTypeList.get(threatSubTypeList.size() - 1);
        assertThat(testThreatSubType.getThreatTypeCode()).isEqualTo(UPDATED_THREAT_TYPE_CODE);
        assertThat(testThreatSubType.getThreatSubTypeCode()).isEqualTo(UPDATED_THREAT_SUB_TYPE_CODE);
        assertThat(testThreatSubType.getThreatSubTypeName()).isEqualTo(UPDATED_THREAT_SUB_TYPE_NAME);
        assertThat(testThreatSubType.getThreatSubTypeDescription()).isEqualTo(UPDATED_THREAT_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingThreatSubType() throws Exception {
        int databaseSizeBeforeUpdate = threatSubTypeRepository.findAll().size();

        // Create the ThreatSubType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThreatSubTypeMockMvc.perform(put("/api/threat-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatSubType)))
            .andExpect(status().isBadRequest());

        // Validate the ThreatSubType in the database
        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThreatSubType() throws Exception {
        // Initialize the database
        threatSubTypeRepository.saveAndFlush(threatSubType);

        int databaseSizeBeforeDelete = threatSubTypeRepository.findAll().size();

        // Get the threatSubType
        restThreatSubTypeMockMvc.perform(delete("/api/threat-sub-types/{id}", threatSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThreatSubType> threatSubTypeList = threatSubTypeRepository.findAll();
        assertThat(threatSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThreatSubType.class);
        ThreatSubType threatSubType1 = new ThreatSubType();
        threatSubType1.setId(1L);
        ThreatSubType threatSubType2 = new ThreatSubType();
        threatSubType2.setId(threatSubType1.getId());
        assertThat(threatSubType1).isEqualTo(threatSubType2);
        threatSubType2.setId(2L);
        assertThat(threatSubType1).isNotEqualTo(threatSubType2);
        threatSubType1.setId(null);
        assertThat(threatSubType1).isNotEqualTo(threatSubType2);
    }
}
