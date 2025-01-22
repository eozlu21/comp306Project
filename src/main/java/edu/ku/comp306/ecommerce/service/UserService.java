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
}
