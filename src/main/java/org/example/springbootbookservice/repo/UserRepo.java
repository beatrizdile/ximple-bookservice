package org.example.springbootbookservice.repo;

import org.example.springbootbookservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
