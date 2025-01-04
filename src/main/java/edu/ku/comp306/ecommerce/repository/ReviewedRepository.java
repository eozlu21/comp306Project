package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Reviewed;
import edu.ku.comp306.ecommerce.entity.ReviewedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewedRepository extends JpaRepository<Reviewed, ReviewedId> {
    // Find reviews by user ID
    List<Reviewed> findByUserId(Integer userId);

    // Find reviews by product ID
    List<Reviewed> findByProductId(Integer productId);
}