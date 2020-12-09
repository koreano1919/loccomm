package it.nero.loccomm.web.rest;

import it.nero.loccomm.domain.ProductAttrs;
import it.nero.loccomm.repository.ProductAttrsRepository;
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
 * REST controller for managing {@link it.nero.loccomm.domain.ProductAttrs}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductAttrsResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttrsResource.class);

    private static final String ENTITY_NAME = "productAttrs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAttrsRepository productAttrsRepository;

    public ProductAttrsResource(ProductAttrsRepository productAttrsRepository) {
        this.productAttrsRepository = productAttrsRepository;
    }

    /**
     * {@code POST  /product-attrs} : Create a new productAttrs.
     *
     * @param productAttrs the productAttrs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAttrs, or with status {@code 400 (Bad Request)} if the productAttrs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-attrs")
    public ResponseEntity<ProductAttrs> createProductAttrs(@RequestBody ProductAttrs productAttrs) throws URISyntaxException {
        log.debug("REST request to save ProductAttrs : {}", productAttrs);
        if (productAttrs.getId() != null) {
            throw new BadRequestAlertException("A new productAttrs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttrs result = productAttrsRepository.save(productAttrs);
        return ResponseEntity.created(new URI("/api/product-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-attrs} : Updates an existing productAttrs.
     *
     * @param productAttrs the productAttrs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAttrs,
     * or with status {@code 400 (Bad Request)} if the productAttrs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAttrs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-attrs")
    public ResponseEntity<ProductAttrs> updateProductAttrs(@RequestBody ProductAttrs productAttrs) throws URISyntaxException {
        log.debug("REST request to update ProductAttrs : {}", productAttrs);
        if (productAttrs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttrs result = productAttrsRepository.save(productAttrs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAttrs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-attrs} : get all the productAttrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAttrs in body.
     */
    @GetMapping("/product-attrs")
    public List<ProductAttrs> getAllProductAttrs() {
        log.debug("REST request to get all ProductAttrs");
        return productAttrsRepository.findAll();
    }

    /**
     * {@code GET  /product-attrs/:id} : get the "id" productAttrs.
     *
     * @param id the id of the productAttrs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAttrs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-attrs/{id}")
    public ResponseEntity<ProductAttrs> getProductAttrs(@PathVariable Long id) {
        log.debug("REST request to get ProductAttrs : {}", id);
        Optional<ProductAttrs> productAttrs = productAttrsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productAttrs);
    }

    /**
     * {@code DELETE  /product-attrs/:id} : delete the "id" productAttrs.
     *
     * @param id the id of the productAttrs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-attrs/{id}")
    public ResponseEntity<Void> deleteProductAttrs(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttrs : {}", id);
        productAttrsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
