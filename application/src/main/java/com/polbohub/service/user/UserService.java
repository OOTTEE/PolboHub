package com.polbohub.service.user;

import com.polbohub.domain.user.User;
import com.polbohub.domain.user.UserId;
import com.polbohub.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User dto) {
        if (repository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("License number already exists");
        }
        return repository.save(dto);
    }

    public User get(UserId id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> list() {
        return repository.findAll();
    }

    public User update(UserId userId, User newUserData) {
        return repository.findById(userId)
                .map( current -> {
                    if (!current.username().equals(newUserData.username()) && repository.existsByUsername(newUserData.username())) {
                        throw new IllegalArgumentException("Username already exists");
                    }
                    return current;
                })
                .map(_ -> new User(userId, newUserData.username(), newUserData.firstName(), newUserData.lastName(), newUserData.birthDate()))
                .map(repository::save)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public void delete(UserId id) {
        repository.deleteById(id);
    }
}
