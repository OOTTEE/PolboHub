package com.polbohub.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(UserId id);
    User save(User user);
    void deleteById(UserId id);
    List<User> findAll();
    boolean existsByUsername(String username);

}
