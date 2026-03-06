package com.polbohub.persistence.user;

import com.polbohub.domain.user.User;
import com.polbohub.domain.user.UserId;
import com.polbohub.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository repository;

    public UserRepositoryImpl(JpaUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id).map(UserEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserEntityMapper.toDomain(repository.save(UserEntityMapper.toEntity(user)));
    }

    @Override
    public void deleteById(UserId id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream().map(UserEntityMapper::toDomain).toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
