package it.nero.loccomm.web.rest;

import it.nero.loccomm.domain.CartItem;
import it.nero.loccomm.repository.CartItemRepository;
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
 * REST controller for managing {@link it.nero.loccomm.domain.CartItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CartItemResource {

    private final Logger log = LoggerFactory.getLogger(CartItemResource.class);

    private static final String ENTITY_NAME = "cartItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartItemRepository cartItemRepository;

    public CartItemResource(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * {@code POST  /cart-items} : Create a new cartItem.
     *
     * @param cartItem the cartItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartItem, or with status {@code 400 (Bad Request)} if the cartItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-items")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) throws URISyntaxException {
        log.debug("REST request to save CartItem : {}", cartItem);
        if (cartItem.getId() != null) {
            throw new BadRequestAlertException("A new cartItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartItem result = cartItemRepository.save(cartItem);
        return ResponseEntity.created(new URI("/api/cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cart-items} : Updates an existing cartItem.
     *
     * @param cartItem the cartItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartItem,
     * or with status {@code 400 (Bad Request)} if the cartItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-items")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) throws URISyntaxException {
        log.debug("REST request to update CartItem : {}", cartItem);
        if (cartItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartItem result = cartItemRepository.save(cartItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cart-items} : get all the cartItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartItems in body.
     */
    @GetMapping("/cart-items")
    public List<CartItem> getAllCartItems() {
        log.debug("REST request to get all CartItems");
        return cartItemRepository.findAll();
    }

    /**
     * {@code GET  /cart-items/:id} : get the "id" cartItem.
     *
     * @param id the id of the cartItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long id) {
        log.debug("REST request to get CartItem : {}", id);
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cartItem);
    }

    /**
     * {@code DELETE  /cart-items/:id} : delete the "id" cartItem.
     *
     * @param id the id of the cartItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        log.debug("REST request to delete CartItem : {}", id);
        cartItemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
