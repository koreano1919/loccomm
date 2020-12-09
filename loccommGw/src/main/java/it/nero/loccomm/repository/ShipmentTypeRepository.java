package it.nero.loccomm.repository;

import it.nero.loccomm.domain.ShipmentType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShipmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {
}
