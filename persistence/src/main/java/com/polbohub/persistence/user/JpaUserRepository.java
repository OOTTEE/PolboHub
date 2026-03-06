package com.polbohub.persistence.user;

import com.polbohub.domain.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, UserId> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
