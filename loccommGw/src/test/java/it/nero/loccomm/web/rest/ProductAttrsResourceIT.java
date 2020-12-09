package it.nero.loccomm.web.rest;

import it.nero.loccomm.LoccommGwApp;
import it.nero.loccomm.domain.ProductAttrs;
import it.nero.loccomm.repository.ProductAttrsRepository;

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
 * Integration tests for the {@link ProductAttrsResource} REST controller.
 */
@SpringBootTest(classes = LoccommGwApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductAttrsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_ID = 2L;

    @Autowired
    private ProductAttrsRepository productAttrsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAttrsMockMvc;

    private ProductAttrs productAttrs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttrs createEntity(EntityManager em) {
        ProductAttrs productAttrs = new ProductAttrs()
            .productId(DEFAULT_PRODUCT_ID)
            .productAttributeId(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        return productAttrs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttrs createUpdatedEntity(EntityManager em) {
        ProductAttrs productAttrs = new ProductAttrs()
            .productId(UPDATED_PRODUCT_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID);
        return productAttrs;
    }

    @BeforeEach
    public void initTest() {
        productAttrs = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttrs() throws Exception {
        int databaseSizeBeforeCreate = productAttrsRepository.findAll().size();
        // Create the ProductAttrs
        restProductAttrsMockMvc.perform(post("/api/product-attrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttrs)))
            .andExpect(status().isCreated());

        // Validate the ProductAttrs in the database
        List<ProductAttrs> productAttrsList = productAttrsRepository.findAll();
        assertThat(productAttrsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttrs testProductAttrs = productAttrsList.get(productAttrsList.size() - 1);
        assertThat(testProductAttrs.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProductAttrs.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void createProductAttrsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttrsRepository.findAll().size();

        // Create the ProductAttrs with an existing ID
        productAttrs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttrsMockMvc.perform(post("/api/product-attrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttrs)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttrs in the database
        List<ProductAttrs> productAttrsList = productAttrsRepository.findAll();
        assertThat(productAttrsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductAttrs() throws Exception {
        // Initialize the database
        productAttrsRepository.saveAndFlush(productAttrs);

        // Get all the productAttrsList
        restProductAttrsMockMvc.perform(get("/api/product-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttrs.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getProductAttrs() throws Exception {
        // Initialize the database
        productAttrsRepository.saveAndFlush(productAttrs);

        // Get the productAttrs
        restProductAttrsMockMvc.perform(get("/api/product-attrs/{id}", productAttrs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAttrs.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeId").value(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProductAttrs() throws Exception {
        // Get the productAttrs
        restProductAttrsMockMvc.perform(get("/api/product-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttrs() throws Exception {
        // Initialize the database
        productAttrsRepository.saveAndFlush(productAttrs);

        int databaseSizeBeforeUpdate = productAttrsRepository.findAll().size();

        // Update the productAttrs
        ProductAttrs updatedProductAttrs = productAttrsRepository.findById(productAttrs.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttrs are not directly saved in db
        em.detach(updatedProductAttrs);
        updatedProductAttrs
            .productId(UPDATED_PRODUCT_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID);

        restProductAttrsMockMvc.perform(put("/api/product-attrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAttrs)))
            .andExpect(status().isOk());

        // Validate the ProductAttrs in the database
        List<ProductAttrs> productAttrsList = productAttrsRepository.findAll();
        assertThat(productAttrsList).hasSize(databaseSizeBeforeUpdate);
        ProductAttrs testProductAttrs = productAttrsList.get(productAttrsList.size() - 1);
        assertThat(testProductAttrs.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProductAttrs.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttrs() throws Exception {
        int databaseSizeBeforeUpdate = productAttrsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttrsMockMvc.perform(put("/api/product-attrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productAttrs)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttrs in the database
        List<ProductAttrs> productAttrsList = productAttrsRepository.findAll();
        assertThat(productAttrsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttrs() throws Exception {
        // Initialize the database
        productAttrsRepository.saveAndFlush(productAttrs);

        int databaseSizeBeforeDelete = productAttrsRepository.findAll().size();

        // Delete the productAttrs
        restProductAttrsMockMvc.perform(delete("/api/product-attrs/{id}", productAttrs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttrs> productAttrsList = productAttrsRepository.findAll();
        assertThat(productAttrsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
