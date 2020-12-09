package it.nero.loccomm.product.repository;

import it.nero.loccomm.product.domain.ProductAttributeType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductAttributeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAttributeTypeRepository extends JpaRepository<ProductAttributeType, Long> {
}
