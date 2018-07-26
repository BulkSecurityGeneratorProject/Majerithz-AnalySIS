package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.Safeguard;
import com.empleodigital.murcia.majerithz.domain.SafeguardSubType;
import com.empleodigital.murcia.majerithz.repository.SafeguardRepository;
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
 * Test class for the SafeguardResource REST controller.
 *
 * @see SafeguardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class SafeguardResourceIntTest {

    private static final SafeguardsTypeCode DEFAULT_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.H;
    private static final SafeguardsTypeCode UPDATED_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.D;

    private static final String DEFAULT_SAFEGUARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SAFEGUARD_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_COMMENTARY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION = 1;

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD = 1;

    @Autowired
    private SafeguardRepository safeguardRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSafeguardMockMvc;

    private Safeguard safeguard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SafeguardResource safeguardResource = new SafeguardResource(safeguardRepository);
        this.restSafeguardMockMvc = MockMvcBuilders.standaloneSetup(safeguardResource)
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
    public static Safeguard createEntity(EntityManager em) {
        Safeguard safeguard = new Safeguard()
            .safeguardsTypeCode(DEFAULT_SAFEGUARDS_TYPE_CODE)
            .safeguardName(DEFAULT_SAFEGUARD_NAME)
            .safeguardCommentary(DEFAULT_SAFEGUARD_COMMENTARY)
            .effectivenessAgainstDegradation(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
        // Add required entity
        SafeguardSubType safeguardSubType = SafeguardSubTypeResourceIntTest.createEntity(em);
        em.persist(safeguardSubType);
        em.flush();
        safeguard.setSafeguardSubType(safeguardSubType);
        return safeguard;
    }

    @Before
    public void initTest() {
        safeguard = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeguard() throws Exception {
        int databaseSizeBeforeCreate = safeguardRepository.findAll().size();

        // Create the Safeguard
        restSafeguardMockMvc.perform(post("/api/safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguard)))
            .andExpect(status().isCreated());

        // Validate the Safeguard in the database
        List<Safeguard> safeguardList = safeguardRepository.findAll();
        assertThat(safeguardList).hasSize(databaseSizeBeforeCreate + 1);
        Safeguard testSafeguard = safeguardList.get(safeguardList.size() - 1);
        assertThat(testSafeguard.getSafeguardsTypeCode()).isEqualTo(DEFAULT_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguard.getSafeguardName()).isEqualTo(DEFAULT_SAFEGUARD_NAME);
        assertThat(testSafeguard.getSafeguardCommentary()).isEqualTo(DEFAULT_SAFEGUARD_COMMENTARY);
        assertThat(testSafeguard.getEffectivenessAgainstDegradation()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testSafeguard.getEffectivenessAgainstLikelihood()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void createSafeguardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeguardRepository.findAll().size();

        // Create the Safeguard with an existing ID
        safeguard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeguardMockMvc.perform(post("/api/safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguard)))
            .andExpect(status().isBadRequest());

        // Validate the Safeguard in the database
        List<Safeguard> safeguardList = safeguardRepository.findAll();
        assertThat(safeguardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSafeguards() throws Exception {
        // Initialize the database
        safeguardRepository.saveAndFlush(safeguard);

        // Get all the safeguardList
        restSafeguardMockMvc.perform(get("/api/safeguards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeguard.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeguardsTypeCode").value(hasItem(DEFAULT_SAFEGUARDS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].safeguardName").value(hasItem(DEFAULT_SAFEGUARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].safeguardCommentary").value(hasItem(DEFAULT_SAFEGUARD_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].effectivenessAgainstDegradation").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)))
            .andExpect(jsonPath("$.[*].effectivenessAgainstLikelihood").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD)));
    }
    

    @Test
    @Transactional
    public void getSafeguard() throws Exception {
        // Initialize the database
        safeguardRepository.saveAndFlush(safeguard);

        // Get the safeguard
        restSafeguardMockMvc.perform(get("/api/safeguards/{id}", safeguard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(safeguard.getId().intValue()))
            .andExpect(jsonPath("$.safeguardsTypeCode").value(DEFAULT_SAFEGUARDS_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.safeguardName").value(DEFAULT_SAFEGUARD_NAME.toString()))
            .andExpect(jsonPath("$.safeguardCommentary").value(DEFAULT_SAFEGUARD_COMMENTARY.toString()))
            .andExpect(jsonPath("$.effectivenessAgainstDegradation").value(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION))
            .andExpect(jsonPath("$.effectivenessAgainstLikelihood").value(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD));
    }
    @Test
    @Transactional
    public void getNonExistingSafeguard() throws Exception {
        // Get the safeguard
        restSafeguardMockMvc.perform(get("/api/safeguards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeguard() throws Exception {
        // Initialize the database
        safeguardRepository.saveAndFlush(safeguard);

        int databaseSizeBeforeUpdate = safeguardRepository.findAll().size();

        // Update the safeguard
        Safeguard updatedSafeguard = safeguardRepository.findById(safeguard.getId()).get();
        // Disconnect from session so that the updates on updatedSafeguard are not directly saved in db
        em.detach(updatedSafeguard);
        updatedSafeguard
            .safeguardsTypeCode(UPDATED_SAFEGUARDS_TYPE_CODE)
            .safeguardName(UPDATED_SAFEGUARD_NAME)
            .safeguardCommentary(UPDATED_SAFEGUARD_COMMENTARY)
            .effectivenessAgainstDegradation(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);

        restSafeguardMockMvc.perform(put("/api/safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSafeguard)))
            .andExpect(status().isOk());

        // Validate the Safeguard in the database
        List<Safeguard> safeguardList = safeguardRepository.findAll();
        assertThat(safeguardList).hasSize(databaseSizeBeforeUpdate);
        Safeguard testSafeguard = safeguardList.get(safeguardList.size() - 1);
        assertThat(testSafeguard.getSafeguardsTypeCode()).isEqualTo(UPDATED_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguard.getSafeguardName()).isEqualTo(UPDATED_SAFEGUARD_NAME);
        assertThat(testSafeguard.getSafeguardCommentary()).isEqualTo(UPDATED_SAFEGUARD_COMMENTARY);
        assertThat(testSafeguard.getEffectivenessAgainstDegradation()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testSafeguard.getEffectivenessAgainstLikelihood()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeguard() throws Exception {
        int databaseSizeBeforeUpdate = safeguardRepository.findAll().size();

        // Create the Safeguard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSafeguardMockMvc.perform(put("/api/safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguard)))
            .andExpect(status().isBadRequest());

        // Validate the Safeguard in the database
        List<Safeguard> safeguardList = safeguardRepository.findAll();
        assertThat(safeguardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeguard() throws Exception {
        // Initialize the database
        safeguardRepository.saveAndFlush(safeguard);

        int databaseSizeBeforeDelete = safeguardRepository.findAll().size();

        // Get the safeguard
        restSafeguardMockMvc.perform(delete("/api/safeguards/{id}", safeguard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Safeguard> safeguardList = safeguardRepository.findAll();
        assertThat(safeguardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Safeguard.class);
        Safeguard safeguard1 = new Safeguard();
        safeguard1.setId(1L);
        Safeguard safeguard2 = new Safeguard();
        safeguard2.setId(safeguard1.getId());
        assertThat(safeguard1).isEqualTo(safeguard2);
        safeguard2.setId(2L);
        assertThat(safeguard1).isNotEqualTo(safeguard2);
        safeguard1.setId(null);
        assertThat(safeguard1).isNotEqualTo(safeguard2);
    }
}
