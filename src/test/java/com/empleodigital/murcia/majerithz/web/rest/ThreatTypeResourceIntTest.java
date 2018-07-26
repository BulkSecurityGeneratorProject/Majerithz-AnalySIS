package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.ThreatType;
import com.empleodigital.murcia.majerithz.repository.ThreatTypeRepository;
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
 * Test class for the ThreatTypeResource REST controller.
 *
 * @see ThreatTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class ThreatTypeResourceIntTest {

    private static final ThreatsTypeCode DEFAULT_THREAT_TYPE_CODE = ThreatsTypeCode.N;
    private static final ThreatsTypeCode UPDATED_THREAT_TYPE_CODE = ThreatsTypeCode.I;

    private static final String DEFAULT_THREAT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THREAT_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ThreatTypeRepository threatTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThreatTypeMockMvc;

    private ThreatType threatType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThreatTypeResource threatTypeResource = new ThreatTypeResource(threatTypeRepository);
        this.restThreatTypeMockMvc = MockMvcBuilders.standaloneSetup(threatTypeResource)
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
    public static ThreatType createEntity(EntityManager em) {
        ThreatType threatType = new ThreatType()
            .threatTypeCode(DEFAULT_THREAT_TYPE_CODE)
            .threatTypeName(DEFAULT_THREAT_TYPE_NAME)
            .threatTypeDescription(DEFAULT_THREAT_TYPE_DESCRIPTION);
        return threatType;
    }

    @Before
    public void initTest() {
        threatType = createEntity(em);
    }

    @Test
    @Transactional
    public void createThreatType() throws Exception {
        int databaseSizeBeforeCreate = threatTypeRepository.findAll().size();

        // Create the ThreatType
        restThreatTypeMockMvc.perform(post("/api/threat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatType)))
            .andExpect(status().isCreated());

        // Validate the ThreatType in the database
        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ThreatType testThreatType = threatTypeList.get(threatTypeList.size() - 1);
        assertThat(testThreatType.getThreatTypeCode()).isEqualTo(DEFAULT_THREAT_TYPE_CODE);
        assertThat(testThreatType.getThreatTypeName()).isEqualTo(DEFAULT_THREAT_TYPE_NAME);
        assertThat(testThreatType.getThreatTypeDescription()).isEqualTo(DEFAULT_THREAT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createThreatTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = threatTypeRepository.findAll().size();

        // Create the ThreatType with an existing ID
        threatType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThreatTypeMockMvc.perform(post("/api/threat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatType)))
            .andExpect(status().isBadRequest());

        // Validate the ThreatType in the database
        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThreatTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = threatTypeRepository.findAll().size();
        // set the field null
        threatType.setThreatTypeName(null);

        // Create the ThreatType, which fails.

        restThreatTypeMockMvc.perform(post("/api/threat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatType)))
            .andExpect(status().isBadRequest());

        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThreatTypes() throws Exception {
        // Initialize the database
        threatTypeRepository.saveAndFlush(threatType);

        // Get all the threatTypeList
        restThreatTypeMockMvc.perform(get("/api/threat-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(threatType.getId().intValue())))
            .andExpect(jsonPath("$.[*].threatTypeCode").value(hasItem(DEFAULT_THREAT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].threatTypeName").value(hasItem(DEFAULT_THREAT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].threatTypeDescription").value(hasItem(DEFAULT_THREAT_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getThreatType() throws Exception {
        // Initialize the database
        threatTypeRepository.saveAndFlush(threatType);

        // Get the threatType
        restThreatTypeMockMvc.perform(get("/api/threat-types/{id}", threatType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(threatType.getId().intValue()))
            .andExpect(jsonPath("$.threatTypeCode").value(DEFAULT_THREAT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.threatTypeName").value(DEFAULT_THREAT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.threatTypeDescription").value(DEFAULT_THREAT_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingThreatType() throws Exception {
        // Get the threatType
        restThreatTypeMockMvc.perform(get("/api/threat-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThreatType() throws Exception {
        // Initialize the database
        threatTypeRepository.saveAndFlush(threatType);

        int databaseSizeBeforeUpdate = threatTypeRepository.findAll().size();

        // Update the threatType
        ThreatType updatedThreatType = threatTypeRepository.findById(threatType.getId()).get();
        // Disconnect from session so that the updates on updatedThreatType are not directly saved in db
        em.detach(updatedThreatType);
        updatedThreatType
            .threatTypeCode(UPDATED_THREAT_TYPE_CODE)
            .threatTypeName(UPDATED_THREAT_TYPE_NAME)
            .threatTypeDescription(UPDATED_THREAT_TYPE_DESCRIPTION);

        restThreatTypeMockMvc.perform(put("/api/threat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThreatType)))
            .andExpect(status().isOk());

        // Validate the ThreatType in the database
        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeUpdate);
        ThreatType testThreatType = threatTypeList.get(threatTypeList.size() - 1);
        assertThat(testThreatType.getThreatTypeCode()).isEqualTo(UPDATED_THREAT_TYPE_CODE);
        assertThat(testThreatType.getThreatTypeName()).isEqualTo(UPDATED_THREAT_TYPE_NAME);
        assertThat(testThreatType.getThreatTypeDescription()).isEqualTo(UPDATED_THREAT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingThreatType() throws Exception {
        int databaseSizeBeforeUpdate = threatTypeRepository.findAll().size();

        // Create the ThreatType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThreatTypeMockMvc.perform(put("/api/threat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threatType)))
            .andExpect(status().isBadRequest());

        // Validate the ThreatType in the database
        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThreatType() throws Exception {
        // Initialize the database
        threatTypeRepository.saveAndFlush(threatType);

        int databaseSizeBeforeDelete = threatTypeRepository.findAll().size();

        // Get the threatType
        restThreatTypeMockMvc.perform(delete("/api/threat-types/{id}", threatType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThreatType> threatTypeList = threatTypeRepository.findAll();
        assertThat(threatTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThreatType.class);
        ThreatType threatType1 = new ThreatType();
        threatType1.setId(1L);
        ThreatType threatType2 = new ThreatType();
        threatType2.setId(threatType1.getId());
        assertThat(threatType1).isEqualTo(threatType2);
        threatType2.setId(2L);
        assertThat(threatType1).isNotEqualTo(threatType2);
        threatType1.setId(null);
        assertThat(threatType1).isNotEqualTo(threatType2);
    }
}
