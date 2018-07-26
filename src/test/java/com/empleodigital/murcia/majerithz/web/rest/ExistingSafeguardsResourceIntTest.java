package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.ExistingSafeguards;
import com.empleodigital.murcia.majerithz.domain.Asset;
import com.empleodigital.murcia.majerithz.repository.ExistingSafeguardsRepository;
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

/**
 * Test class for the ExistingSafeguardsResource REST controller.
 *
 * @see ExistingSafeguardsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class ExistingSafeguardsResourceIntTest {

    private static final String DEFAULT_EXISTING_SAFEGUARDS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXISTING_SAFEGUARDS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXISTING_SAFEGUARDS_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_EXISTING_SAFEGUARDS_COMMENTARY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION = 1;

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD = 1;

    @Autowired
    private ExistingSafeguardsRepository existingSafeguardsRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExistingSafeguardsMockMvc;

    private ExistingSafeguards existingSafeguards;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExistingSafeguardsResource existingSafeguardsResource = new ExistingSafeguardsResource(existingSafeguardsRepository);
        this.restExistingSafeguardsMockMvc = MockMvcBuilders.standaloneSetup(existingSafeguardsResource)
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
    public static ExistingSafeguards createEntity(EntityManager em) {
        ExistingSafeguards existingSafeguards = new ExistingSafeguards()
            .existingSafeguardsName(DEFAULT_EXISTING_SAFEGUARDS_NAME)
            .existingSafeguardsCommentary(DEFAULT_EXISTING_SAFEGUARDS_COMMENTARY)
            .effectivenessAgainstDegradation(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
        // Add required entity
        Asset asset = AssetResourceIntTest.createEntity(em);
        em.persist(asset);
        em.flush();
        existingSafeguards.setAsset(asset);
        return existingSafeguards;
    }

    @Before
    public void initTest() {
        existingSafeguards = createEntity(em);
    }

    @Test
    @Transactional
    public void createExistingSafeguards() throws Exception {
        int databaseSizeBeforeCreate = existingSafeguardsRepository.findAll().size();

        // Create the ExistingSafeguards
        restExistingSafeguardsMockMvc.perform(post("/api/existing-safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSafeguards)))
            .andExpect(status().isCreated());

        // Validate the ExistingSafeguards in the database
        List<ExistingSafeguards> existingSafeguardsList = existingSafeguardsRepository.findAll();
        assertThat(existingSafeguardsList).hasSize(databaseSizeBeforeCreate + 1);
        ExistingSafeguards testExistingSafeguards = existingSafeguardsList.get(existingSafeguardsList.size() - 1);
        assertThat(testExistingSafeguards.getExistingSafeguardsName()).isEqualTo(DEFAULT_EXISTING_SAFEGUARDS_NAME);
        assertThat(testExistingSafeguards.getExistingSafeguardsCommentary()).isEqualTo(DEFAULT_EXISTING_SAFEGUARDS_COMMENTARY);
        assertThat(testExistingSafeguards.getEffectivenessAgainstDegradation()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testExistingSafeguards.getEffectivenessAgainstLikelihood()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void createExistingSafeguardsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = existingSafeguardsRepository.findAll().size();

        // Create the ExistingSafeguards with an existing ID
        existingSafeguards.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExistingSafeguardsMockMvc.perform(post("/api/existing-safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSafeguards)))
            .andExpect(status().isBadRequest());

        // Validate the ExistingSafeguards in the database
        List<ExistingSafeguards> existingSafeguardsList = existingSafeguardsRepository.findAll();
        assertThat(existingSafeguardsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExistingSafeguards() throws Exception {
        // Initialize the database
        existingSafeguardsRepository.saveAndFlush(existingSafeguards);

        // Get all the existingSafeguardsList
        restExistingSafeguardsMockMvc.perform(get("/api/existing-safeguards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(existingSafeguards.getId().intValue())))
            .andExpect(jsonPath("$.[*].existingSafeguardsName").value(hasItem(DEFAULT_EXISTING_SAFEGUARDS_NAME.toString())))
            .andExpect(jsonPath("$.[*].existingSafeguardsCommentary").value(hasItem(DEFAULT_EXISTING_SAFEGUARDS_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].effectivenessAgainstDegradation").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)))
            .andExpect(jsonPath("$.[*].effectivenessAgainstLikelihood").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD)));
    }
    

    @Test
    @Transactional
    public void getExistingSafeguards() throws Exception {
        // Initialize the database
        existingSafeguardsRepository.saveAndFlush(existingSafeguards);

        // Get the existingSafeguards
        restExistingSafeguardsMockMvc.perform(get("/api/existing-safeguards/{id}", existingSafeguards.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(existingSafeguards.getId().intValue()))
            .andExpect(jsonPath("$.existingSafeguardsName").value(DEFAULT_EXISTING_SAFEGUARDS_NAME.toString()))
            .andExpect(jsonPath("$.existingSafeguardsCommentary").value(DEFAULT_EXISTING_SAFEGUARDS_COMMENTARY.toString()))
            .andExpect(jsonPath("$.effectivenessAgainstDegradation").value(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION))
            .andExpect(jsonPath("$.effectivenessAgainstLikelihood").value(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD));
    }
    @Test
    @Transactional
    public void getNonExistingExistingSafeguards() throws Exception {
        // Get the existingSafeguards
        restExistingSafeguardsMockMvc.perform(get("/api/existing-safeguards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExistingSafeguards() throws Exception {
        // Initialize the database
        existingSafeguardsRepository.saveAndFlush(existingSafeguards);

        int databaseSizeBeforeUpdate = existingSafeguardsRepository.findAll().size();

        // Update the existingSafeguards
        ExistingSafeguards updatedExistingSafeguards = existingSafeguardsRepository.findById(existingSafeguards.getId()).get();
        // Disconnect from session so that the updates on updatedExistingSafeguards are not directly saved in db
        em.detach(updatedExistingSafeguards);
        updatedExistingSafeguards
            .existingSafeguardsName(UPDATED_EXISTING_SAFEGUARDS_NAME)
            .existingSafeguardsCommentary(UPDATED_EXISTING_SAFEGUARDS_COMMENTARY)
            .effectivenessAgainstDegradation(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);

        restExistingSafeguardsMockMvc.perform(put("/api/existing-safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExistingSafeguards)))
            .andExpect(status().isOk());

        // Validate the ExistingSafeguards in the database
        List<ExistingSafeguards> existingSafeguardsList = existingSafeguardsRepository.findAll();
        assertThat(existingSafeguardsList).hasSize(databaseSizeBeforeUpdate);
        ExistingSafeguards testExistingSafeguards = existingSafeguardsList.get(existingSafeguardsList.size() - 1);
        assertThat(testExistingSafeguards.getExistingSafeguardsName()).isEqualTo(UPDATED_EXISTING_SAFEGUARDS_NAME);
        assertThat(testExistingSafeguards.getExistingSafeguardsCommentary()).isEqualTo(UPDATED_EXISTING_SAFEGUARDS_COMMENTARY);
        assertThat(testExistingSafeguards.getEffectivenessAgainstDegradation()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testExistingSafeguards.getEffectivenessAgainstLikelihood()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void updateNonExistingExistingSafeguards() throws Exception {
        int databaseSizeBeforeUpdate = existingSafeguardsRepository.findAll().size();

        // Create the ExistingSafeguards

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExistingSafeguardsMockMvc.perform(put("/api/existing-safeguards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSafeguards)))
            .andExpect(status().isBadRequest());

        // Validate the ExistingSafeguards in the database
        List<ExistingSafeguards> existingSafeguardsList = existingSafeguardsRepository.findAll();
        assertThat(existingSafeguardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExistingSafeguards() throws Exception {
        // Initialize the database
        existingSafeguardsRepository.saveAndFlush(existingSafeguards);

        int databaseSizeBeforeDelete = existingSafeguardsRepository.findAll().size();

        // Get the existingSafeguards
        restExistingSafeguardsMockMvc.perform(delete("/api/existing-safeguards/{id}", existingSafeguards.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExistingSafeguards> existingSafeguardsList = existingSafeguardsRepository.findAll();
        assertThat(existingSafeguardsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExistingSafeguards.class);
        ExistingSafeguards existingSafeguards1 = new ExistingSafeguards();
        existingSafeguards1.setId(1L);
        ExistingSafeguards existingSafeguards2 = new ExistingSafeguards();
        existingSafeguards2.setId(existingSafeguards1.getId());
        assertThat(existingSafeguards1).isEqualTo(existingSafeguards2);
        existingSafeguards2.setId(2L);
        assertThat(existingSafeguards1).isNotEqualTo(existingSafeguards2);
        existingSafeguards1.setId(null);
        assertThat(existingSafeguards1).isNotEqualTo(existingSafeguards2);
    }
}
