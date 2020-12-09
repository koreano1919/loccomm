package it.nero.loccomm.product.web.rest;

import it.nero.loccomm.product.domain.ProductAttributeType;
import it.nero.loccomm.product.repository.ProductAttributeTypeRepository;
import it.nero.loccomm.product.web.rest.errors.BadRequestAlertException;

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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link it.nero.loccomm.product.domain.ProductAttributeType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductAttributeTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeTypeResource.class);

    private static final String ENTITY_NAME = "loccommProductProductAttributeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAttributeTypeRepository productAttributeTypeRepository;

    public ProductAttributeTypeResource(ProductAttributeTypeRepository productAttributeTypeRepository) {
        this.productAttributeTypeRepository = productAttributeTypeRepository;
    }

    /**
     * {@code POST  /product-attribute-types} : Create a new productAttributeType.
     *
     * @param productAttributeType the productAttributeType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAttributeType, or with status {@code 400 (Bad Request)} if the productAttributeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-attribute-types")
    public ResponseEntity<ProductAttributeType> createProductAttributeType(@RequestBody ProductAttributeType productAttributeType) throws URISyntaxException {
        log.debug("REST request to save ProductAttributeType : {}", productAttributeType);
        if (productAttributeType.getId() != null) {
            throw new BadRequestAlertException("A new productAttributeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttributeType result = productAttributeTypeRepository.save(productAttributeType);
        return ResponseEntity.created(new URI("/api/product-attribute-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-attribute-types} : Updates an existing productAttributeType.
     *
     * @param productAttributeType the productAttributeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAttributeType,
     * or with status {@code 400 (Bad Request)} if the productAttributeType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAttributeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-attribute-types")
    public ResponseEntity<ProductAttributeType> updateProductAttributeType(@RequestBody ProductAttributeType productAttributeType) throws URISyntaxException {
        log.debug("REST request to update ProductAttributeType : {}", productAttributeType);
        if (productAttributeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttributeType result = productAttributeTypeRepository.save(productAttributeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAttributeType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-attribute-types} : get all the productAttributeTypes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAttributeTypes in body.
     */
    @GetMapping("/product-attribute-types")
    public List<ProductAttributeType> getAllProductAttributeTypes(@RequestParam(required = false) String filter) {
        if ("productattribute-is-null".equals(filter)) {
            log.debug("REST request to get all ProductAttributeTypes where productAttribute is null");
            return StreamSupport
                .stream(productAttributeTypeRepository.findAll().spliterator(), false)
                .filter(productAttributeType -> productAttributeType.getProductAttribute() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ProductAttributeTypes");
        return productAttributeTypeRepository.findAll();
    }

    /**
     * {@code GET  /product-attribute-types/:id} : get the "id" productAttributeType.
     *
     * @param id the id of the productAttributeType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAttributeType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-attribute-types/{id}")
    public ResponseEntity<ProductAttributeType> getProductAttributeType(@PathVariable Long id) {
        log.debug("REST request to get ProductAttributeType : {}", id);
        Optional<ProductAttributeType> productAttributeType = productAttributeTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAttributeType);
    }

    /**
     * {@code DELETE  /product-attribute-types/:id} : delete the "id" productAttributeType.
     *
     * @param id the id of the productAttributeType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-attribute-types/{id}")
    public ResponseEntity<Void> deleteProductAttributeType(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttributeType : {}", id);
        productAttributeTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
