package it.nero.loccomm.web.rest;

import it.nero.loccomm.LoccommGwApp;
import it.nero.loccomm.domain.ProductAttributeType;
import it.nero.loccomm.repository.ProductAttributeTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductAttributeTypeResource} REST controller.
 */
@SpringBootTest(classes = LoccommGwApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAttributeTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeTypeRepository productAttributeTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAttributeTypeMockMvc;

    private ProductAttributeType productAttributeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttributeType createEntity(EntityManager em) {
        ProductAttributeType productAttributeType = new ProductAttributeType()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return productAttributeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttributeType createUpdatedEntity(EntityManager em) {
        ProductAttributeType productAttributeType = new ProductAttributeType()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        return productAttributeType;
    }

    @BeforeEach
    public void initTest() {
        productAttributeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttributeType() throws Exception {
        int databaseSizeBeforeCreate = productAttributeTypeRepository.findAll().size();
        // Create the ProductAttributeType
        restProductAttributeTypeMockMvc.perform(post("/api/product-attribute-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeType)))
            .andExpect(status().isCreated());

        // Validate the ProductAttributeType in the database
        List<ProductAttributeType> productAttributeTypeList = productAttributeTypeRepository.findAll();
        assertThat(productAttributeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttributeType testProductAttributeType = productAttributeTypeList.get(productAttributeTypeList.size() - 1);
        assertThat(testProductAttributeType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAttributeType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testProductAttributeType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductAttributeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeTypeRepository.findAll().size();

        // Create the ProductAttributeType with an existing ID
        productAttributeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeTypeMockMvc.perform(post("/api/product-attribute-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeType in the database
        List<ProductAttributeType> productAttributeTypeList = productAttributeTypeRepository.findAll();
        assertThat(productAttributeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductAttributeTypes() throws Exception {
        // Initialize the database
        productAttributeTypeRepository.saveAndFlush(productAttributeType);

        // Get all the productAttributeTypeList
        restProductAttributeTypeMockMvc.perform(get("/api/product-attribute-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttributeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProductAttributeType() throws Exception {
        // Initialize the database
        productAttributeTypeRepository.saveAndFlush(productAttributeType);

        // Get the productAttributeType
        restProductAttributeTypeMockMvc.perform(get("/api/product-attribute-types/{id}", productAttributeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAttributeType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingProductAttributeType() throws Exception {
        // Get the productAttributeType
        restProductAttributeTypeMockMvc.perform(get("/api/product-attribute-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttributeType() throws Exception {
        // Initialize the database
        productAttributeTypeRepository.saveAndFlush(productAttributeType);

        int databaseSizeBeforeUpdate = productAttributeTypeRepository.findAll().size();

        // Update the productAttributeType
        ProductAttributeType updatedProductAttributeType = productAttributeTypeRepository.findById(productAttributeType.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttributeType are not directly saved in db
        em.detach(updatedProductAttributeType);
        updatedProductAttributeType
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);

        restProductAttributeTypeMockMvc.perform(put("/api/product-attribute-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAttributeType)))
            .andExpect(status().isOk());

        // Validate the ProductAttributeType in the database
        List<ProductAttributeType> productAttributeTypeList = productAttributeTypeRepository.findAll();
        assertThat(productAttributeTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttributeType testProductAttributeType = productAttributeTypeList.get(productAttributeTypeList.size() - 1);
        assertThat(testProductAttributeType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAttributeType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testProductAttributeType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttributeType() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeTypeMockMvc.perform(put("/api/product-attribute-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeType in the database
        List<ProductAttributeType> productAttributeTypeList = productAttributeTypeRepository.findAll();
        assertThat(productAttributeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttributeType() throws Exception {
        // Initialize the database
        productAttributeTypeRepository.saveAndFlush(productAttributeType);

        int databaseSizeBeforeDelete = productAttributeTypeRepository.findAll().size();

        // Delete the productAttributeType
        restProductAttributeTypeMockMvc.perform(delete("/api/product-attribute-types/{id}", productAttributeType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttributeType> productAttributeTypeList = productAttributeTypeRepository.findAll();
        assertThat(productAttributeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
