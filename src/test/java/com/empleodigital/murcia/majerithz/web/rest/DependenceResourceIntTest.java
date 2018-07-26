package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.Dependence;
import com.empleodigital.murcia.majerithz.domain.Asset;
import com.empleodigital.murcia.majerithz.repository.DependenceRepository;
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
 * Test class for the DependenceResource REST controller.
 *
 * @see DependenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class DependenceResourceIntTest {

    private static final Integer DEFAULT_DEGREE = 0;
    private static final Integer UPDATED_DEGREE = 1;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private DependenceRepository dependenceRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDependenceMockMvc;

    private Dependence dependence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DependenceResource dependenceResource = new DependenceResource(dependenceRepository);
        this.restDependenceMockMvc = MockMvcBuilders.standaloneSetup(dependenceResource)
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
    public static Dependence createEntity(EntityManager em) {
        Dependence dependence = new Dependence()
            .degree(DEFAULT_DEGREE)
            .reason(DEFAULT_REASON);
        // Add required entity
        Asset asset = AssetResourceIntTest.createEntity(em);
        em.persist(asset);
        em.flush();
        dependence.setAsset(asset);
        return dependence;
    }

    @Before
    public void initTest() {
        dependence = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependence() throws Exception {
        int databaseSizeBeforeCreate = dependenceRepository.findAll().size();

        // Create the Dependence
        restDependenceMockMvc.perform(post("/api/dependences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependence)))
            .andExpect(status().isCreated());

        // Validate the Dependence in the database
        List<Dependence> dependenceList = dependenceRepository.findAll();
        assertThat(dependenceList).hasSize(databaseSizeBeforeCreate + 1);
        Dependence testDependence = dependenceList.get(dependenceList.size() - 1);
        assertThat(testDependence.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testDependence.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createDependenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependenceRepository.findAll().size();

        // Create the Dependence with an existing ID
        dependence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependenceMockMvc.perform(post("/api/dependences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependence)))
            .andExpect(status().isBadRequest());

        // Validate the Dependence in the database
        List<Dependence> dependenceList = dependenceRepository.findAll();
        assertThat(dependenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDependences() throws Exception {
        // Initialize the database
        dependenceRepository.saveAndFlush(dependence);

        // Get all the dependenceList
        restDependenceMockMvc.perform(get("/api/dependences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependence.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }
    

    @Test
    @Transactional
    public void getDependence() throws Exception {
        // Initialize the database
        dependenceRepository.saveAndFlush(dependence);

        // Get the dependence
        restDependenceMockMvc.perform(get("/api/dependences/{id}", dependence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dependence.getId().intValue()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDependence() throws Exception {
        // Get the dependence
        restDependenceMockMvc.perform(get("/api/dependences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependence() throws Exception {
        // Initialize the database
        dependenceRepository.saveAndFlush(dependence);

        int databaseSizeBeforeUpdate = dependenceRepository.findAll().size();

        // Update the dependence
        Dependence updatedDependence = dependenceRepository.findById(dependence.getId()).get();
        // Disconnect from session so that the updates on updatedDependence are not directly saved in db
        em.detach(updatedDependence);
        updatedDependence
            .degree(UPDATED_DEGREE)
            .reason(UPDATED_REASON);

        restDependenceMockMvc.perform(put("/api/dependences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDependence)))
            .andExpect(status().isOk());

        // Validate the Dependence in the database
        List<Dependence> dependenceList = dependenceRepository.findAll();
        assertThat(dependenceList).hasSize(databaseSizeBeforeUpdate);
        Dependence testDependence = dependenceList.get(dependenceList.size() - 1);
        assertThat(testDependence.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testDependence.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingDependence() throws Exception {
        int databaseSizeBeforeUpdate = dependenceRepository.findAll().size();

        // Create the Dependence

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDependenceMockMvc.perform(put("/api/dependences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependence)))
            .andExpect(status().isBadRequest());

        // Validate the Dependence in the database
        List<Dependence> dependenceList = dependenceRepository.findAll();
        assertThat(dependenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDependence() throws Exception {
        // Initialize the database
        dependenceRepository.saveAndFlush(dependence);

        int databaseSizeBeforeDelete = dependenceRepository.findAll().size();

        // Get the dependence
        restDependenceMockMvc.perform(delete("/api/dependences/{id}", dependence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dependence> dependenceList = dependenceRepository.findAll();
        assertThat(dependenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependence.class);
        Dependence dependence1 = new Dependence();
        dependence1.setId(1L);
        Dependence dependence2 = new Dependence();
        dependence2.setId(dependence1.getId());
        assertThat(dependence1).isEqualTo(dependence2);
        dependence2.setId(2L);
        assertThat(dependence1).isNotEqualTo(dependence2);
        dependence1.setId(null);
        assertThat(dependence1).isNotEqualTo(dependence2);
    }
}
