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

    public void updateMembershipType(int userId) {
        Double totalSpending = userRepository.calculateTotalSpendingForLast30Days(userId);

        // Handle null case when there are no orders
        totalSpending = (totalSpending == null) ? 0 : totalSpending;

        MembershipType membershipType = totalSpending > 10000 ? MembershipType.PREMIUM :
                totalSpending > 5000 ? MembershipType.GOLD :
                        MembershipType.SILVER;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setMembershipType(membershipType);
        userRepository.save(user);
    }
}
