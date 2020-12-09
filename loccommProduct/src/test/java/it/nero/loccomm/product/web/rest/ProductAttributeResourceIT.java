package it.nero.loccomm.product.web.rest;

import it.nero.loccomm.product.LoccommProductApp;
import it.nero.loccomm.product.domain.ProductAttribute;
import it.nero.loccomm.product.repository.ProductAttributeRepository;

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
 * Integration tests for the {@link ProductAttributeResource} REST controller.
 */
@SpringBootTest(classes = LoccommProductApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAttributeResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAttributeMockMvc;

    private ProductAttribute productAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttribute createEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .productId(DEFAULT_PRODUCT_ID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return productAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttribute createUpdatedEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .productId(UPDATED_PRODUCT_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        return productAttribute;
    }

    @BeforeEach
    public void initTest() {
        productAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();
        // Create the ProductAttribute
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProductAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testProductAttribute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductAttribute.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute with an existing ID
        productAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductAttributes() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAttribute.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingProductAttribute() throws Exception {
        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Update the productAttribute
        ProductAttribute updatedProductAttribute = productAttributeRepository.findById(productAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttribute are not directly saved in db
        em.detach(updatedProductAttribute);
        updatedProductAttribute
            .productId(UPDATED_PRODUCT_ID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);

        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAttribute)))
            .andExpect(status().isOk());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProductAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAttribute.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testProductAttribute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductAttribute.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeDelete = productAttributeRepository.findAll().size();

        // Delete the productAttribute
        restProductAttributeMockMvc.perform(delete("/api/product-attributes/{id}", productAttribute.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
