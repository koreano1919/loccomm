package it.nero.loccomm.web.rest;

import it.nero.loccomm.domain.ShipmentType;
import it.nero.loccomm.repository.ShipmentTypeRepository;
import it.nero.loccomm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.nero.loccomm.domain.ShipmentType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShipmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentTypeResource.class);

    private static final String ENTITY_NAME = "shipmentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentTypeRepository shipmentTypeRepository;

    public ShipmentTypeResource(ShipmentTypeRepository shipmentTypeRepository) {
        this.shipmentTypeRepository = shipmentTypeRepository;
    }

    /**
     * {@code POST  /shipment-types} : Create a new shipmentType.
     *
     * @param shipmentType the shipmentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipmentType, or with status {@code 400 (Bad Request)} if the shipmentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentType> createShipmentType(@RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to save ShipmentType : {}", shipmentType);
        if (shipmentType.getId() != null) {
            throw new BadRequestAlertException("A new shipmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentType result = shipmentTypeRepository.save(shipmentType);
        return ResponseEntity.created(new URI("/api/shipment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipment-types} : Updates an existing shipmentType.
     *
     * @param shipmentType the shipmentType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentType,
     * or with status {@code 400 (Bad Request)} if the shipmentType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipment-types")
    public ResponseEntity<ShipmentType> updateShipmentType(@RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to update ShipmentType : {}", shipmentType);
        if (shipmentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentType result = shipmentTypeRepository.save(shipmentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shipment-types} : get all the shipmentTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipmentTypes in body.
     */
    @GetMapping("/shipment-types")
    public List<ShipmentType> getAllShipmentTypes() {
        log.debug("REST request to get all ShipmentTypes");
        return shipmentTypeRepository.findAll();
    }

    /**
     * {@code GET  /shipment-types/:id} : get the "id" shipmentType.
     *
     * @param id the id of the shipmentType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipment-types/{id}")
    public ResponseEntity<ShipmentType> getShipmentType(@PathVariable Long id) {
        log.debug("REST request to get ShipmentType : {}", id);
        Optional<ShipmentType> shipmentType = shipmentTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shipmentType);
    }

    /**
     * {@code DELETE  /shipment-types/:id} : delete the "id" shipmentType.
     *
     * @param id the id of the shipmentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipment-types/{id}")
    public ResponseEntity<Void> deleteShipmentType(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentType : {}", id);
        shipmentTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
