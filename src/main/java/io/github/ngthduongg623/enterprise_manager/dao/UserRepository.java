package io.github.ngthduongg623.enterprise_manager.dao;

import io.github.ngthduongg623.enterprise_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
