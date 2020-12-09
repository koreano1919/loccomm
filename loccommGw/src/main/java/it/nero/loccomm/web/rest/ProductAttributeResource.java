package it.nero.loccomm.web.rest;

import it.nero.loccomm.domain.ProductAttribute;
import it.nero.loccomm.repository.ProductAttributeRepository;
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
 * REST controller for managing {@link it.nero.loccomm.domain.ProductAttribute}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeResource.class);

    private static final String ENTITY_NAME = "productAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttributeResource(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    /**
     * {@code POST  /product-attributes} : Create a new productAttribute.
     *
     * @param productAttribute the productAttribute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAttribute, or with status {@code 400 (Bad Request)} if the productAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-attributes")
    public ResponseEntity<ProductAttribute> createProductAttribute(@RequestBody ProductAttribute productAttribute) throws URISyntaxException {
        log.debug("REST request to save ProductAttribute : {}", productAttribute);
        if (productAttribute.getId() != null) {
            throw new BadRequestAlertException("A new productAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttribute result = productAttributeRepository.save(productAttribute);
        return ResponseEntity.created(new URI("/api/product-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-attributes} : Updates an existing productAttribute.
     *
     * @param productAttribute the productAttribute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAttribute,
     * or with status {@code 400 (Bad Request)} if the productAttribute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAttribute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-attributes")
    public ResponseEntity<ProductAttribute> updateProductAttribute(@RequestBody ProductAttribute productAttribute) throws URISyntaxException {
        log.debug("REST request to update ProductAttribute : {}", productAttribute);
        if (productAttribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttribute result = productAttributeRepository.save(productAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAttribute.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-attributes} : get all the productAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAttributes in body.
     */
    @GetMapping("/product-attributes")
    public List<ProductAttribute> getAllProductAttributes() {
        log.debug("REST request to get all ProductAttributes");
        return productAttributeRepository.findAll();
    }

    /**
     * {@code GET  /product-attributes/:id} : get the "id" productAttribute.
     *
     * @param id the id of the productAttribute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAttribute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-attributes/{id}")
    public ResponseEntity<ProductAttribute> getProductAttribute(@PathVariable Long id) {
        log.debug("REST request to get ProductAttribute : {}", id);
        Optional<ProductAttribute> productAttribute = productAttributeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAttribute);
    }

    /**
     * {@code DELETE  /product-attributes/:id} : delete the "id" productAttribute.
     *
     * @param id the id of the productAttribute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-attributes/{id}")
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttribute : {}", id);
        productAttributeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
