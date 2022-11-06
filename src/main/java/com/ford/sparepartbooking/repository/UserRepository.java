package com.ford.sparepartbooking.repository;

import com.ford.sparepartbooking.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String name);

}
