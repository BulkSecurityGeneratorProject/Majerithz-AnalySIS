package com.empleodigital.murcia.majerithz.web.rest;

import com.empleodigital.murcia.majerithz.MajerithzAnalySisApp;

import com.empleodigital.murcia.majerithz.domain.SafeguardSubType;
import com.empleodigital.murcia.majerithz.repository.SafeguardSubTypeRepository;
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
 * Test class for the SafeguardSubTypeResource REST controller.
 *
 * @see SafeguardSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MajerithzAnalySisApp.class)
public class SafeguardSubTypeResourceIntTest {

    private static final SafeguardsTypeCode DEFAULT_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.H;
    private static final SafeguardsTypeCode UPDATED_SAFEGUARDS_TYPE_CODE = SafeguardsTypeCode.D;

    private static final String DEFAULT_CODE_SAFEGUARD_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SAFEGUARD_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SAFEGUARD_SUB_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_SUB_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SAFEGUARD_SUB_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SAFEGUARD_SUB_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SafeguardSubTypeRepository safeguardSubTypeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSafeguardSubTypeMockMvc;

    private SafeguardSubType safeguardSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SafeguardSubTypeResource safeguardSubTypeResource = new SafeguardSubTypeResource(safeguardSubTypeRepository);
        this.restSafeguardSubTypeMockMvc = MockMvcBuilders.standaloneSetup(safeguardSubTypeResource)
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
    public static SafeguardSubType createEntity(EntityManager em) {
        SafeguardSubType safeguardSubType = new SafeguardSubType()
            .safeguardsTypeCode(DEFAULT_SAFEGUARDS_TYPE_CODE)
            .codeSafeguardSubType(DEFAULT_CODE_SAFEGUARD_SUB_TYPE)
            .safeguardSubTypeName(DEFAULT_SAFEGUARD_SUB_TYPE_NAME)
            .safeguardSubTypeDescription(DEFAULT_SAFEGUARD_SUB_TYPE_DESCRIPTION);
        return safeguardSubType;
    }

    @Before
    public void initTest() {
        safeguardSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSafeguardSubType() throws Exception {
        int databaseSizeBeforeCreate = safeguardSubTypeRepository.findAll().size();

        // Create the SafeguardSubType
        restSafeguardSubTypeMockMvc.perform(post("/api/safeguard-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardSubType)))
            .andExpect(status().isCreated());

        // Validate the SafeguardSubType in the database
        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SafeguardSubType testSafeguardSubType = safeguardSubTypeList.get(safeguardSubTypeList.size() - 1);
        assertThat(testSafeguardSubType.getSafeguardsTypeCode()).isEqualTo(DEFAULT_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguardSubType.getCodeSafeguardSubType()).isEqualTo(DEFAULT_CODE_SAFEGUARD_SUB_TYPE);
        assertThat(testSafeguardSubType.getSafeguardSubTypeName()).isEqualTo(DEFAULT_SAFEGUARD_SUB_TYPE_NAME);
        assertThat(testSafeguardSubType.getSafeguardSubTypeDescription()).isEqualTo(DEFAULT_SAFEGUARD_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSafeguardSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = safeguardSubTypeRepository.findAll().size();

        // Create the SafeguardSubType with an existing ID
        safeguardSubType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeguardSubTypeMockMvc.perform(post("/api/safeguard-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardSubType)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardSubType in the database
        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSafeguardSubTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeguardSubTypeRepository.findAll().size();
        // set the field null
        safeguardSubType.setSafeguardSubTypeName(null);

        // Create the SafeguardSubType, which fails.

        restSafeguardSubTypeMockMvc.perform(post("/api/safeguard-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardSubType)))
            .andExpect(status().isBadRequest());

        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSafeguardSubTypes() throws Exception {
        // Initialize the database
        safeguardSubTypeRepository.saveAndFlush(safeguardSubType);

        // Get all the safeguardSubTypeList
        restSafeguardSubTypeMockMvc.perform(get("/api/safeguard-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safeguardSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeguardsTypeCode").value(hasItem(DEFAULT_SAFEGUARDS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].codeSafeguardSubType").value(hasItem(DEFAULT_CODE_SAFEGUARD_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].safeguardSubTypeName").value(hasItem(DEFAULT_SAFEGUARD_SUB_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].safeguardSubTypeDescription").value(hasItem(DEFAULT_SAFEGUARD_SUB_TYPE_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getSafeguardSubType() throws Exception {
        // Initialize the database
        safeguardSubTypeRepository.saveAndFlush(safeguardSubType);

        // Get the safeguardSubType
        restSafeguardSubTypeMockMvc.perform(get("/api/safeguard-sub-types/{id}", safeguardSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(safeguardSubType.getId().intValue()))
            .andExpect(jsonPath("$.safeguardsTypeCode").value(DEFAULT_SAFEGUARDS_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.codeSafeguardSubType").value(DEFAULT_CODE_SAFEGUARD_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.safeguardSubTypeName").value(DEFAULT_SAFEGUARD_SUB_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.safeguardSubTypeDescription").value(DEFAULT_SAFEGUARD_SUB_TYPE_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSafeguardSubType() throws Exception {
        // Get the safeguardSubType
        restSafeguardSubTypeMockMvc.perform(get("/api/safeguard-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSafeguardSubType() throws Exception {
        // Initialize the database
        safeguardSubTypeRepository.saveAndFlush(safeguardSubType);

        int databaseSizeBeforeUpdate = safeguardSubTypeRepository.findAll().size();

        // Update the safeguardSubType
        SafeguardSubType updatedSafeguardSubType = safeguardSubTypeRepository.findById(safeguardSubType.getId()).get();
        // Disconnect from session so that the updates on updatedSafeguardSubType are not directly saved in db
        em.detach(updatedSafeguardSubType);
        updatedSafeguardSubType
            .safeguardsTypeCode(UPDATED_SAFEGUARDS_TYPE_CODE)
            .codeSafeguardSubType(UPDATED_CODE_SAFEGUARD_SUB_TYPE)
            .safeguardSubTypeName(UPDATED_SAFEGUARD_SUB_TYPE_NAME)
            .safeguardSubTypeDescription(UPDATED_SAFEGUARD_SUB_TYPE_DESCRIPTION);

        restSafeguardSubTypeMockMvc.perform(put("/api/safeguard-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSafeguardSubType)))
            .andExpect(status().isOk());

        // Validate the SafeguardSubType in the database
        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeUpdate);
        SafeguardSubType testSafeguardSubType = safeguardSubTypeList.get(safeguardSubTypeList.size() - 1);
        assertThat(testSafeguardSubType.getSafeguardsTypeCode()).isEqualTo(UPDATED_SAFEGUARDS_TYPE_CODE);
        assertThat(testSafeguardSubType.getCodeSafeguardSubType()).isEqualTo(UPDATED_CODE_SAFEGUARD_SUB_TYPE);
        assertThat(testSafeguardSubType.getSafeguardSubTypeName()).isEqualTo(UPDATED_SAFEGUARD_SUB_TYPE_NAME);
        assertThat(testSafeguardSubType.getSafeguardSubTypeDescription()).isEqualTo(UPDATED_SAFEGUARD_SUB_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSafeguardSubType() throws Exception {
        int databaseSizeBeforeUpdate = safeguardSubTypeRepository.findAll().size();

        // Create the SafeguardSubType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSafeguardSubTypeMockMvc.perform(put("/api/safeguard-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(safeguardSubType)))
            .andExpect(status().isBadRequest());

        // Validate the SafeguardSubType in the database
        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSafeguardSubType() throws Exception {
        // Initialize the database
        safeguardSubTypeRepository.saveAndFlush(safeguardSubType);

        int databaseSizeBeforeDelete = safeguardSubTypeRepository.findAll().size();

        // Get the safeguardSubType
        restSafeguardSubTypeMockMvc.perform(delete("/api/safeguard-sub-types/{id}", safeguardSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SafeguardSubType> safeguardSubTypeList = safeguardSubTypeRepository.findAll();
        assertThat(safeguardSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SafeguardSubType.class);
        SafeguardSubType safeguardSubType1 = new SafeguardSubType();
        safeguardSubType1.setId(1L);
        SafeguardSubType safeguardSubType2 = new SafeguardSubType();
        safeguardSubType2.setId(safeguardSubType1.getId());
        assertThat(safeguardSubType1).isEqualTo(safeguardSubType2);
        safeguardSubType2.setId(2L);
        assertThat(safeguardSubType1).isNotEqualTo(safeguardSubType2);
        safeguardSubType1.setId(null);
        assertThat(safeguardSubType1).isNotEqualTo(safeguardSubType2);
    }
}
