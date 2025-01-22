package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * Calculate total spending for the user in the last 30 days.
     */
    @Query(value = """
        SELECT SUM(order_total) AS total_spending
        FROM (
            SELECT
                o.OrderID,
                SUM(oc.Quantity * p.Price) AS order_total
            FROM Orders o
            JOIN OrderContains oc ON o.OrderID = oc.OrderID
            JOIN Product p ON oc.ProductID = p.ProductID
            WHERE o.UserID = :userId
              AND o.OrderDate >= CURRENT_DATE - INTERVAL 30 DAY
            GROUP BY o.OrderID
            HAVING SUM(oc.Quantity * p.Price) > 0
        ) AS subquery
        """, nativeQuery = true)
    Double calculateTotalSpendingForLast30Days(@Param("userId") int userId);

    /**
     * Find users who purchased all categories (Phone, Laptop, Camera).
     * Excludes users who have zero orders.
     */
    @Query(value = """
        SELECT U.UserID
        FROM User U
        /* Ensure user has at least one order */
        WHERE EXISTS (
            SELECT 1
            FROM Orders O
            WHERE O.UserID = U.UserID
        )
        /* Then check they bought each category at least once */
        AND NOT EXISTS (
            SELECT DISTINCT Category
            FROM (
                SELECT 'Phone' AS Category
                UNION
                SELECT 'Laptop'
                UNION
                SELECT 'Camera'
            ) AS Categories
            WHERE NOT EXISTS (
                SELECT 1
                FROM Orders O
                JOIN OrderContains OC ON O.OrderID = OC.OrderID
                JOIN Product P ON P.ProductID = OC.ProductID
                WHERE O.UserID = U.UserID
                  AND (
                    (P.ProductID IN (SELECT ProductID FROM Phone)   AND Categories.Category = 'Phone')
                    OR (P.ProductID IN (SELECT ProductID FROM Laptop) AND Categories.Category = 'Laptop')
                    OR (P.ProductID IN (SELECT ProductID FROM Camera) AND Categories.Category = 'Camera')
                  )
            )
        )
        """, nativeQuery = true)
    List<Integer> findUsersWhoPurchasedAllCategories();

    /**
     * Find users who have reviewed ALL products they actually ordered.
     * i.e. "There is no ordered product for which the user lacks a review."
     */
    @Query(value = """
        SELECT U.UserID
        FROM User U
        /* Ensure user has at least one order so it's not trivially true */
        WHERE EXISTS (
            SELECT 1
            FROM Orders O
            WHERE O.UserID = U.UserID
        )
        /* For every product in Orders->OrderContains, user must have a review */
        AND NOT EXISTS (
            SELECT 1
            FROM Orders O2
            JOIN OrderContains OC2 ON O2.OrderID = OC2.OrderID
            WHERE O2.UserID = U.UserID
              /* If we don't find a matching review, user fails */
              AND NOT EXISTS (
                  SELECT 1
                  FROM Reviewed R
                  WHERE R.UserID = U.UserID
                    AND R.ProductID = OC2.ProductID
              )
        )
        """, nativeQuery = true)
    List<Integer> findUsersWhoReviewedAllProductsTheyOrdered();

    /**
     * Find users who did not review ANY product they ordered.
     * i.e. user has at least one order, but zero matching entries in Reviewed for the products they've bought.
     */
    @Query(value = """
        SELECT DISTINCT U.UserID
        FROM User U
        /* Ensure user has at least one order */
        JOIN Orders O ON O.UserID = U.UserID
        /* Exclude anyone who has at least one reviewed product that they ordered */
        WHERE NOT EXISTS (
            SELECT 1
            FROM Orders O2
            JOIN OrderContains OC2 ON O2.OrderID = OC2.OrderID
            JOIN Reviewed R ON R.ProductID = OC2.ProductID
            WHERE O2.UserID = U.UserID
              AND R.UserID = U.UserID
        )
        """, nativeQuery = true)
    List<Integer> findUsersWhoDidNotReviewAnyProductTheyOrdered();
}
