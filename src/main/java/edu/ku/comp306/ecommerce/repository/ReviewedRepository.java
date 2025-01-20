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
                u.membershipType AS membershipType,
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

    @Query(value = """
            SELECT
                u.username AS username,
                u.membershipType AS membershipType,
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
                r.UserID = :userId
            """, nativeQuery = true)
    List<UserReviewDTO> findReviewsByUser(@Param("userId") Integer userId);

    @Query(value = """
            SELECT
                AVG(r.ReviewRating)
            FROM
                Reviewed r
            WHERE
                r.ProductID = :productId
            """, nativeQuery = true)
    Double getAverageRating(Integer productId);

    @Query(value = """
            SELECT
                COUNT(r.ReviewRating)
            FROM
                Reviewed r
            WHERE
                r.ProductID = :productId
            """, nativeQuery = true)
    long getRatedCount(Integer productId);
}