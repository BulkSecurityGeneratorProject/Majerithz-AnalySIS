package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.Threat;
import com.empleodigital.murcia.majerithz.domain.ThreatSubType;
import com.empleodigital.murcia.majerithz.domain.Asset;
import com.empleodigital.murcia.majerithz.repository.ThreatRepository;
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
 * Test class for the ThreatResource REST controller.
 *
 * @see ThreatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class ThreatResourceIntTest {

    private static final ThreatsTypeCode DEFAULT_THREAT_TYPE_CODE = ThreatsTypeCode.N;
    private static final ThreatsTypeCode UPDATED_THREAT_TYPE_CODE = ThreatsTypeCode.I;

    private static final String DEFAULT_THREAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THREAT_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_THREAT_COMMENTARY = "BBBBBBBBBB";

    private static final Integer DEFAULT_ASSET_DEGRADATION = 0;
    private static final Integer UPDATED_ASSET_DEGRADATION = 1;

    private static final Integer DEFAULT_THEORETICAL_LIKELIHOOD = 0;
    private static final Integer UPDATED_THEORETICAL_LIKELIHOOD = 1;

    private static final Double DEFAULT_POTENTIAL_IMPACT = 0D;
    private static final Double UPDATED_POTENTIAL_IMPACT = 1D;

    @Autowired
    private ThreatRepository threatRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThreatMockMvc;

    private Threat threat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThreatResource threatResource = new ThreatResource(threatRepository);
        this.restThreatMockMvc = MockMvcBuilders.standaloneSetup(threatResource)
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
    public static Threat createEntity(EntityManager em) {
        Threat threat = new Threat()
            .threatTypeCode(DEFAULT_THREAT_TYPE_CODE)
            .threatName(DEFAULT_THREAT_NAME)
            .threatCommentary(DEFAULT_THREAT_COMMENTARY)
            .assetDegradation(DEFAULT_ASSET_DEGRADATION)
            .theoreticalLikelihood(DEFAULT_THEORETICAL_LIKELIHOOD)
            .potentialImpact(DEFAULT_POTENTIAL_IMPACT);
        // Add required entity
        ThreatSubType threatSubType = ThreatSubTypeResourceIntTest.createEntity(em);
        em.persist(threatSubType);
        em.flush();
        threat.setThreatSubType(threatSubType);
        // Add required entity
        Asset asset = AssetResourceIntTest.createEntity(em);
        em.persist(asset);
        em.flush();
        threat.setAsset(asset);
        return threat;
    }

    @Before
    public void initTest() {
        threat = createEntity(em);
    }

    @Test
    @Transactional
    public void createThreat() throws Exception {
        int databaseSizeBeforeCreate = threatRepository.findAll().size();

        // Create the Threat
        restThreatMockMvc.perform(post("/api/threats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threat)))
            .andExpect(status().isCreated());

        // Validate the Threat in the database
        List<Threat> threatList = threatRepository.findAll();
        assertThat(threatList).hasSize(databaseSizeBeforeCreate + 1);
        Threat testThreat = threatList.get(threatList.size() - 1);
        assertThat(testThreat.getThreatTypeCode()).isEqualTo(DEFAULT_THREAT_TYPE_CODE);
        assertThat(testThreat.getThreatName()).isEqualTo(DEFAULT_THREAT_NAME);
        assertThat(testThreat.getThreatCommentary()).isEqualTo(DEFAULT_THREAT_COMMENTARY);
        assertThat(testThreat.getAssetDegradation()).isEqualTo(DEFAULT_ASSET_DEGRADATION);
        assertThat(testThreat.getTheoreticalLikelihood()).isEqualTo(DEFAULT_THEORETICAL_LIKELIHOOD);
        assertThat(testThreat.getPotentialImpact()).isEqualTo(DEFAULT_POTENTIAL_IMPACT);
    }

    @Test
    @Transactional
    public void createThreatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = threatRepository.findAll().size();

        // Create the Threat with an existing ID
        threat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThreatMockMvc.perform(post("/api/threats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threat)))
            .andExpect(status().isBadRequest());

        // Validate the Threat in the database
        List<Threat> threatList = threatRepository.findAll();
        assertThat(threatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllThreats() throws Exception {
        // Initialize the database
        threatRepository.saveAndFlush(threat);

        // Get all the threatList
        restThreatMockMvc.perform(get("/api/threats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(threat.getId().intValue())))
            .andExpect(jsonPath("$.[*].threatTypeCode").value(hasItem(DEFAULT_THREAT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].threatName").value(hasItem(DEFAULT_THREAT_NAME.toString())))
            .andExpect(jsonPath("$.[*].threatCommentary").value(hasItem(DEFAULT_THREAT_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].assetDegradation").value(hasItem(DEFAULT_ASSET_DEGRADATION)))
            .andExpect(jsonPath("$.[*].theoreticalLikelihood").value(hasItem(DEFAULT_THEORETICAL_LIKELIHOOD)))
            .andExpect(jsonPath("$.[*].potentialImpact").value(hasItem(DEFAULT_POTENTIAL_IMPACT.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getThreat() throws Exception {
        // Initialize the database
        threatRepository.saveAndFlush(threat);

        // Get the threat
        restThreatMockMvc.perform(get("/api/threats/{id}", threat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(threat.getId().intValue()))
            .andExpect(jsonPath("$.threatTypeCode").value(DEFAULT_THREAT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.threatName").value(DEFAULT_THREAT_NAME.toString()))
            .andExpect(jsonPath("$.threatCommentary").value(DEFAULT_THREAT_COMMENTARY.toString()))
            .andExpect(jsonPath("$.assetDegradation").value(DEFAULT_ASSET_DEGRADATION))
            .andExpect(jsonPath("$.theoreticalLikelihood").value(DEFAULT_THEORETICAL_LIKELIHOOD))
            .andExpect(jsonPath("$.potentialImpact").value(DEFAULT_POTENTIAL_IMPACT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingThreat() throws Exception {
        // Get the threat
        restThreatMockMvc.perform(get("/api/threats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThreat() throws Exception {
        // Initialize the database
        threatRepository.saveAndFlush(threat);

        int databaseSizeBeforeUpdate = threatRepository.findAll().size();

        // Update the threat
        Threat updatedThreat = threatRepository.findById(threat.getId()).get();
        // Disconnect from session so that the updates on updatedThreat are not directly saved in db
        em.detach(updatedThreat);
        updatedThreat
            .threatTypeCode(UPDATED_THREAT_TYPE_CODE)
            .threatName(UPDATED_THREAT_NAME)
            .threatCommentary(UPDATED_THREAT_COMMENTARY)
            .assetDegradation(UPDATED_ASSET_DEGRADATION)
            .theoreticalLikelihood(UPDATED_THEORETICAL_LIKELIHOOD)
            .potentialImpact(UPDATED_POTENTIAL_IMPACT);

        restThreatMockMvc.perform(put("/api/threats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThreat)))
            .andExpect(status().isOk());

        // Validate the Threat in the database
        List<Threat> threatList = threatRepository.findAll();
        assertThat(threatList).hasSize(databaseSizeBeforeUpdate);
        Threat testThreat = threatList.get(threatList.size() - 1);
        assertThat(testThreat.getThreatTypeCode()).isEqualTo(UPDATED_THREAT_TYPE_CODE);
        assertThat(testThreat.getThreatName()).isEqualTo(UPDATED_THREAT_NAME);
        assertThat(testThreat.getThreatCommentary()).isEqualTo(UPDATED_THREAT_COMMENTARY);
        assertThat(testThreat.getAssetDegradation()).isEqualTo(UPDATED_ASSET_DEGRADATION);
        assertThat(testThreat.getTheoreticalLikelihood()).isEqualTo(UPDATED_THEORETICAL_LIKELIHOOD);
        assertThat(testThreat.getPotentialImpact()).isEqualTo(UPDATED_POTENTIAL_IMPACT);
    }

    @Test
    @Transactional
    public void updateNonExistingThreat() throws Exception {
        int databaseSizeBeforeUpdate = threatRepository.findAll().size();

        // Create the Threat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThreatMockMvc.perform(put("/api/threats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(threat)))
            .andExpect(status().isBadRequest());

        // Validate the Threat in the database
        List<Threat> threatList = threatRepository.findAll();
        assertThat(threatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThreat() throws Exception {
        // Initialize the database
        threatRepository.saveAndFlush(threat);

        int databaseSizeBeforeDelete = threatRepository.findAll().size();

        // Get the threat
        restThreatMockMvc.perform(delete("/api/threats/{id}", threat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Threat> threatList = threatRepository.findAll();
        assertThat(threatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Threat.class);
        Threat threat1 = new Threat();
        threat1.setId(1L);
        Threat threat2 = new Threat();
        threat2.setId(threat1.getId());
        assertThat(threat1).isEqualTo(threat2);
        threat2.setId(2L);
        assertThat(threat1).isNotEqualTo(threat2);
        threat1.setId(null);
        assertThat(threat1).isNotEqualTo(threat2);
    }
}
