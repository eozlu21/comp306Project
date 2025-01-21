package edu.ku.comp306.ecommerce.entity;

import edu.ku.comp306.ecommerce.enums.MembershipType;
import edu.ku.comp306.ecommerce.enums.NetworkCompatibility;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Fname", length = 20)
    private String firstName;

    @Column(name = "Lname", length = 20)
    private String lastName;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Password", length = 50, nullable = false)
    private String password;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Column(name = "username", nullable = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "MembershipType", nullable = false, columnDefinition = "varchar(255) default 'SILVER'")
    private MembershipType membershipType = MembershipType.SILVER; // Default value
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
