package org.example.springm2a1.repository;

import org.example.springm2a1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
