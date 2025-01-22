package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.entity.User;
import edu.ku.comp306.ecommerce.enums.MembershipType;
import edu.ku.comp306.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
    public String getUserNameById(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getFullName)
                .orElse("Guest");
    }

    public MembershipType getMembershipTypeById(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getMembershipType) // This assumes your `User` entity has a `MembershipType` field
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    public void updateMembershipType(int userId) {
        Double totalSpending = userRepository.calculateTotalSpendingForLast30Days(userId);

        // Handle null case when there are no orders
        totalSpending = (totalSpending == null) ? 0 : totalSpending;

        MembershipType membershipType = totalSpending > 200000 ? MembershipType.PREMIUM :
                totalSpending > 100000 ? MembershipType.GOLD :
                        MembershipType.SILVER;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setMembershipType(membershipType);
        userRepository.save(user);
    }

    public User[] getAllUsers() {
        return userRepository.findAll().toArray(new User[0]);
    }

    public boolean userPurchasedAllCategories(int userId) {
        return userRepository.findUsersWhoPurchasedAllCategories().contains(userId);
    }

    public boolean userReviewedAllProductsTheyOrdered(int userId) {
        return userRepository.findUsersWhoReviewedAllProductsTheyOrdered().contains(userId);
    }

    public boolean userDidNotReviewAnyProductTheyOrdered(int userId) {
        return userRepository.findUsersWhoDidNotReviewAnyProductTheyOrdered().contains(userId);
    }
}
