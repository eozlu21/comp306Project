package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

}