package com.polbohub.service.user;

import com.polbohub.model.User;
import com.polbohub.persistence.entity.UserEntity;
import com.polbohub.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User dto) {
        if (repository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new IllegalArgumentException("License number already exists");
        }
        UserEntity entity = toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        UserEntity saved = repository.save(entity);
        return toDto(saved);
    }

    public User get(UUID id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> list() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public User update(UUID id, User dto) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        // Ensure license uniqueness if changed
        if (!entity.getLicenseNumber().equals(dto.licenseNumber()) && repository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new IllegalArgumentException("License number already exists");
        }
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setBirthDate(dto.birthDate());
        entity.setLicenseNumber(dto.licenseNumber());
        entity.setActive(dto.active());
        return toDto(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private UserEntity toEntity(User dto) {
        UserEntity e = new UserEntity();
        e.setId(dto.id());
        e.setFirstName(dto.firstName());
        e.setLastName(dto.lastName());
        e.setBirthDate(dto.birthDate());
        e.setLicenseNumber(dto.licenseNumber());
        e.setActive(dto.active());
        return e;
    }

    private User toDto(UserEntity e) {
        return new User(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getBirthDate(),
                e.getLicenseNumber(),
                e.isActive()
        );
    }
}
