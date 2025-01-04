package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    // Find shipments by status
    List<Shipment> findByStatus(String status);

    // Find shipments by order ID
    List<Shipment> findByOrderId(Integer orderId);
}