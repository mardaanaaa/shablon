package kz.spring.parfume.repositories;

import kz.spring.parfume.entities.Role;
import kz.spring.parfume.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
}
