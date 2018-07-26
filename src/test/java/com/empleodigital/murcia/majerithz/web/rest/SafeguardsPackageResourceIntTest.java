package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.SafeguardsPackage;
import com.empleodigital.murcia.majerithz.repository.SafeguardsPackageRepository;
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
 * Test class for the SafeguardsPackageResource REST controller.
 *
 * @see SafeguardsPackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class SafeguardsPackageResourceIntTest {

    private static final String DEFAULT_SAFEGUARDS_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARDS_PACKAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SAFEGUARDS_PACKAGE_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARDS_PACKAGE_COMMENTARY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION = 1;

    private static final Integer DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD = 0;
    private static final Integer UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD = 1;

    @Autowired
    private SafeguardsPackageRepository safeguardsPackageRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSafeguardsPackageMockMvc;

    private SafeguardsPackage safeguardsPackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SafeguardsPackageResource safeguardsPackageResource = new SafeguardsPackageResource(safeguardsPackageRepository);
        this.restSafeguardsPackageMockMvc = MockMvcBuilders.standaloneSetup(safeguardsPackageResource)
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
    public static SafeguardsPackage createEntity(EntityManager em) {
        SafeguardsPackage safeguardsPackage = new SafeguardsPackage()
            .safeguardsPackageName(DEFAULT_SAFEGUARDS_PACKAGE_NAME)
            .safeguardsPackageCommentary(DEFAULT_SAFEGUARDS_PACKAGE_COMMENTARY)
            .effectivenessAgainstDegradation(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
        return safeguardsPackage;
    }

    @Before
    public void initTest() {
        safeguardsPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeguardsPackage() throws Exception {
        int databaseSizeBeforeCreate = safeguardsPackageRepository.findAll().size();

        // Create the SafeguardsPackage
        restSafeguardsPackageMockMvc.perform(post("/api/safeguards-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardsPackage)))
            .andExpect(status().isCreated());

        // Validate the SafeguardsPackage in the database
        List<SafeguardsPackage> safeguardsPackageList = safeguardsPackageRepository.findAll();
        assertThat(safeguardsPackageList).hasSize(databaseSizeBeforeCreate + 1);
        SafeguardsPackage testSafeguardsPackage = safeguardsPackageList.get(safeguardsPackageList.size() - 1);
        assertThat(testSafeguardsPackage.getSafeguardsPackageName()).isEqualTo(DEFAULT_SAFEGUARDS_PACKAGE_NAME);
        assertThat(testSafeguardsPackage.getSafeguardsPackageCommentary()).isEqualTo(DEFAULT_SAFEGUARDS_PACKAGE_COMMENTARY);
        assertThat(testSafeguardsPackage.getEffectivenessAgainstDegradation()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testSafeguardsPackage.getEffectivenessAgainstLikelihood()).isEqualTo(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void createSafeguardsPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeguardsPackageRepository.findAll().size();

        // Create the SafeguardsPackage with an existing ID
        safeguardsPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeguardsPackageMockMvc.perform(post("/api/safeguards-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardsPackage)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardsPackage in the database
        List<SafeguardsPackage> safeguardsPackageList = safeguardsPackageRepository.findAll();
        assertThat(safeguardsPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSafeguardsPackages() throws Exception {
        // Initialize the database
        safeguardsPackageRepository.saveAndFlush(safeguardsPackage);

        // Get all the safeguardsPackageList
        restSafeguardsPackageMockMvc.perform(get("/api/safeguards-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeguardsPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeguardsPackageName").value(hasItem(DEFAULT_SAFEGUARDS_PACKAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].safeguardsPackageCommentary").value(hasItem(DEFAULT_SAFEGUARDS_PACKAGE_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].effectivenessAgainstDegradation").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION)))
            .andExpect(jsonPath("$.[*].effectivenessAgainstLikelihood").value(hasItem(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD)));
    }
    

    @Test
    @Transactional
    public void getSafeguardsPackage() throws Exception {
        // Initialize the database
        safeguardsPackageRepository.saveAndFlush(safeguardsPackage);

        // Get the safeguardsPackage
        restSafeguardsPackageMockMvc.perform(get("/api/safeguards-packages/{id}", safeguardsPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(safeguardsPackage.getId().intValue()))
            .andExpect(jsonPath("$.safeguardsPackageName").value(DEFAULT_SAFEGUARDS_PACKAGE_NAME.toString()))
            .andExpect(jsonPath("$.safeguardsPackageCommentary").value(DEFAULT_SAFEGUARDS_PACKAGE_COMMENTARY.toString()))
            .andExpect(jsonPath("$.effectivenessAgainstDegradation").value(DEFAULT_EFFECTIVENESS_AGAINST_DEGRADATION))
            .andExpect(jsonPath("$.effectivenessAgainstLikelihood").value(DEFAULT_EFFECTIVENESS_AGAINST_LIKELIHOOD));
    }
    @Test
    @Transactional
    public void getNonExistingSafeguardsPackage() throws Exception {
        // Get the safeguardsPackage
        restSafeguardsPackageMockMvc.perform(get("/api/safeguards-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeguardsPackage() throws Exception {
        // Initialize the database
        safeguardsPackageRepository.saveAndFlush(safeguardsPackage);

        int databaseSizeBeforeUpdate = safeguardsPackageRepository.findAll().size();

        // Update the safeguardsPackage
        SafeguardsPackage updatedSafeguardsPackage = safeguardsPackageRepository.findById(safeguardsPackage.getId()).get();
        // Disconnect from session so that the updates on updatedSafeguardsPackage are not directly saved in db
        em.detach(updatedSafeguardsPackage);
        updatedSafeguardsPackage
            .safeguardsPackageName(UPDATED_SAFEGUARDS_PACKAGE_NAME)
            .safeguardsPackageCommentary(UPDATED_SAFEGUARDS_PACKAGE_COMMENTARY)
            .effectivenessAgainstDegradation(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION)
            .effectivenessAgainstLikelihood(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);

        restSafeguardsPackageMockMvc.perform(put("/api/safeguards-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSafeguardsPackage)))
            .andExpect(status().isOk());

        // Validate the SafeguardsPackage in the database
        List<SafeguardsPackage> safeguardsPackageList = safeguardsPackageRepository.findAll();
        assertThat(safeguardsPackageList).hasSize(databaseSizeBeforeUpdate);
        SafeguardsPackage testSafeguardsPackage = safeguardsPackageList.get(safeguardsPackageList.size() - 1);
        assertThat(testSafeguardsPackage.getSafeguardsPackageName()).isEqualTo(UPDATED_SAFEGUARDS_PACKAGE_NAME);
        assertThat(testSafeguardsPackage.getSafeguardsPackageCommentary()).isEqualTo(UPDATED_SAFEGUARDS_PACKAGE_COMMENTARY);
        assertThat(testSafeguardsPackage.getEffectivenessAgainstDegradation()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_DEGRADATION);
        assertThat(testSafeguardsPackage.getEffectivenessAgainstLikelihood()).isEqualTo(UPDATED_EFFECTIVENESS_AGAINST_LIKELIHOOD);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeguardsPackage() throws Exception {
        int databaseSizeBeforeUpdate = safeguardsPackageRepository.findAll().size();

        // Create the SafeguardsPackage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSafeguardsPackageMockMvc.perform(put("/api/safeguards-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardsPackage)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardsPackage in the database
        List<SafeguardsPackage> safeguardsPackageList = safeguardsPackageRepository.findAll();
        assertThat(safeguardsPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeguardsPackage() throws Exception {
        // Initialize the database
        safeguardsPackageRepository.saveAndFlush(safeguardsPackage);

        int databaseSizeBeforeDelete = safeguardsPackageRepository.findAll().size();

        // Get the safeguardsPackage
        restSafeguardsPackageMockMvc.perform(delete("/api/safeguards-packages/{id}", safeguardsPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SafeguardsPackage> safeguardsPackageList = safeguardsPackageRepository.findAll();
        assertThat(safeguardsPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeguardsPackage.class);
        SafeguardsPackage safeguardsPackage1 = new SafeguardsPackage();
        safeguardsPackage1.setId(1L);
        SafeguardsPackage safeguardsPackage2 = new SafeguardsPackage();
        safeguardsPackage2.setId(safeguardsPackage1.getId());
        assertThat(safeguardsPackage1).isEqualTo(safeguardsPackage2);
        safeguardsPackage2.setId(2L);
        assertThat(safeguardsPackage1).isNotEqualTo(safeguardsPackage2);
        safeguardsPackage1.setId(null);
        assertThat(safeguardsPackage1).isNotEqualTo(safeguardsPackage2);
    }
}
