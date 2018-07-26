package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.Analysis;
import com.empleodigital.murcia.majerithz.domain.Asset;
import com.empleodigital.murcia.majerithz.domain.Company;
import com.empleodigital.murcia.majerithz.repository.AnalysisRepository;
import com.empleodigital.murcia.majerithz.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static com.empleodigital.murcia.majerithz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnalysisResource REST controller.
 *
 * @see AnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class AnalysisResourceIntTest {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Instant DEFAULT_ANALYSIS_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANALYSIS_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ANALYSIS_LAST_EDIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANALYSIS_LAST_EDIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STEP = "AAAAAAAAAA";
    private static final String UPDATED_STEP = "BBBBBBBBBB";

    @Autowired
    private AnalysisRepository analysisRepository;
    @Mock
    private AnalysisRepository analysisRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnalysisMockMvc;

    private Analysis analysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalysisResource analysisResource = new AnalysisResource(analysisRepository);
        this.restAnalysisMockMvc = MockMvcBuilders.standaloneSetup(analysisResource)
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
    public static Analysis createEntity(EntityManager em) {
        Analysis analysis = new Analysis()
            .identifier(DEFAULT_IDENTIFIER)
            .analysisCreationDate(DEFAULT_ANALYSIS_CREATION_DATE)
            .analysisLastEdit(DEFAULT_ANALYSIS_LAST_EDIT)
            .step(DEFAULT_STEP);
        // Add required entity
        Asset asset = AssetResourceIntTest.createEntity(em);
        em.persist(asset);
        em.flush();
        analysis.getAssets().add(asset);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        analysis.setCompany(company);
        return analysis;
    }

    @Before
    public void initTest() {
        analysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalysis() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis
        restAnalysisMockMvc.perform(post("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isCreated());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testAnalysis.getAnalysisCreationDate()).isEqualTo(DEFAULT_ANALYSIS_CREATION_DATE);
        assertThat(testAnalysis.getAnalysisLastEdit()).isEqualTo(DEFAULT_ANALYSIS_LAST_EDIT);
        assertThat(testAnalysis.getStep()).isEqualTo(DEFAULT_STEP);
    }

    @Test
    @Transactional
    public void createAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis with an existing ID
        analysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisMockMvc.perform(post("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnalyses() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get all the analysisList
        restAnalysisMockMvc.perform(get("/api/analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].analysisCreationDate").value(hasItem(DEFAULT_ANALYSIS_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].analysisLastEdit").value(hasItem(DEFAULT_ANALYSIS_LAST_EDIT.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP.toString())));
    }
    
    public void getAllAnalysesWithEagerRelationshipsIsEnabled() throws Exception {
        AnalysisResource analysisResource = new AnalysisResource(analysisRepositoryMock);
        when(analysisRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAnalysisMockMvc = MockMvcBuilders.standaloneSetup(analysisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnalysisMockMvc.perform(get("/api/analyses?eagerload=true"))
        .andExpect(status().isOk());

        verify(analysisRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllAnalysesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AnalysisResource analysisResource = new AnalysisResource(analysisRepositoryMock);
            when(analysisRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAnalysisMockMvc = MockMvcBuilders.standaloneSetup(analysisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAnalysisMockMvc.perform(get("/api/analyses?eagerload=true"))
        .andExpect(status().isOk());

            verify(analysisRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(analysis.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.analysisCreationDate").value(DEFAULT_ANALYSIS_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.analysisLastEdit").value(DEFAULT_ANALYSIS_LAST_EDIT.toString()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAnalysis() throws Exception {
        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis
        Analysis updatedAnalysis = analysisRepository.findById(analysis.getId()).get();
        // Disconnect from session so that the updates on updatedAnalysis are not directly saved in db
        em.detach(updatedAnalysis);
        updatedAnalysis
            .identifier(UPDATED_IDENTIFIER)
            .analysisCreationDate(UPDATED_ANALYSIS_CREATION_DATE)
            .analysisLastEdit(UPDATED_ANALYSIS_LAST_EDIT)
            .step(UPDATED_STEP);

        restAnalysisMockMvc.perform(put("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalysis)))
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testAnalysis.getAnalysisCreationDate()).isEqualTo(UPDATED_ANALYSIS_CREATION_DATE);
        assertThat(testAnalysis.getAnalysisLastEdit()).isEqualTo(UPDATED_ANALYSIS_LAST_EDIT);
        assertThat(testAnalysis.getStep()).isEqualTo(UPDATED_STEP);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Create the Analysis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnalysisMockMvc.perform(put("/api/analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(analysis)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.saveAndFlush(analysis);

        int databaseSizeBeforeDelete = analysisRepository.findAll().size();

        // Get the analysis
        restAnalysisMockMvc.perform(delete("/api/analyses/{id}", analysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Analysis.class);
        Analysis analysis1 = new Analysis();
        analysis1.setId(1L);
        Analysis analysis2 = new Analysis();
        analysis2.setId(analysis1.getId());
        assertThat(analysis1).isEqualTo(analysis2);
        analysis2.setId(2L);
        assertThat(analysis1).isNotEqualTo(analysis2);
        analysis1.setId(null);
        assertThat(analysis1).isNotEqualTo(analysis2);
    }
}
