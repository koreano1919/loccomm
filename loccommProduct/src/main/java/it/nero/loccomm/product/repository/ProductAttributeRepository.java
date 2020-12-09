package it.nero.loccomm.product.repository;

import it.nero.loccomm.product.domain.ProductAttribute;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}
