package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.Reviewed;
import edu.ku.comp306.ecommerce.entity.ReviewedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewedRepository extends JpaRepository<Reviewed, ReviewedId> {
    // Find reviews by user ID
    List<Reviewed> findByUserId(Integer userId);

    // Find reviews by product ID
    List<Reviewed> findByProductId(Integer productId);

    @Query(value = """
            SELECT
                u.username AS username,
                r.UserID AS userId,
                r.ProductID AS productId,
                r.ReviewDate AS reviewDate,
                r.ReviewRating AS rating,
                r.Comment AS comment
            FROM
                User u
            JOIN
                Reviewed r ON u.UserID = r.UserID
            WHERE
                r.ProductID = :productId
            """, nativeQuery = true)
    List<UserReviewDTO> findReviewsForProduct(@Param("productId") Integer productId);
}