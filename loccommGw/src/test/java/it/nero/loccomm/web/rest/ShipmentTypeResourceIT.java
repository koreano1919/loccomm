package it.nero.loccomm.web.rest;

import it.nero.loccomm.LoccommGwApp;
import it.nero.loccomm.domain.ShipmentType;
import it.nero.loccomm.repository.ShipmentTypeRepository;

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
 * Integration tests for the {@link ShipmentTypeResource} REST controller.
 */
@SpringBootTest(classes = LoccommGwApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShipmentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ShipmentTypeRepository shipmentTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentTypeMockMvc;

    private ShipmentType shipmentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentType createEntity(EntityManager em) {
        ShipmentType shipmentType = new ShipmentType()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return shipmentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentType createUpdatedEntity(EntityManager em) {
        ShipmentType shipmentType = new ShipmentType()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);
        return shipmentType;
    }

    @BeforeEach
    public void initTest() {
        shipmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentType() throws Exception {
        int databaseSizeBeforeCreate = shipmentTypeRepository.findAll().size();
        // Create the ShipmentType
        restShipmentTypeMockMvc.perform(post("/api/shipment-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipmentType)))
            .andExpect(status().isCreated());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentType testShipmentType = shipmentTypeList.get(shipmentTypeList.size() - 1);
        assertThat(testShipmentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipmentType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testShipmentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createShipmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentTypeRepository.findAll().size();

        // Create the ShipmentType with an existing ID
        shipmentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentTypeMockMvc.perform(post("/api/shipment-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipmentType)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShipmentTypes() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get all the shipmentTypeList
        restShipmentTypeMockMvc.perform(get("/api/shipment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipment-types/{id}", shipmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingShipmentType() throws Exception {
        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        int databaseSizeBeforeUpdate = shipmentTypeRepository.findAll().size();

        // Update the shipmentType
        ShipmentType updatedShipmentType = shipmentTypeRepository.findById(shipmentType.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentType are not directly saved in db
        em.detach(updatedShipmentType);
        updatedShipmentType
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);

        restShipmentTypeMockMvc.perform(put("/api/shipment-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipmentType)))
            .andExpect(status().isOk());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeUpdate);
        ShipmentType testShipmentType = shipmentTypeList.get(shipmentTypeList.size() - 1);
        assertThat(testShipmentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipmentType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testShipmentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentType() throws Exception {
        int databaseSizeBeforeUpdate = shipmentTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentTypeMockMvc.perform(put("/api/shipment-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipmentType)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        int databaseSizeBeforeDelete = shipmentTypeRepository.findAll().size();

        // Delete the shipmentType
        restShipmentTypeMockMvc.perform(delete("/api/shipment-types/{id}", shipmentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
