package com.polbohub.api.v1.user;

import com.polbohub.model.User;
import com.polbohub.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController implements UserApi {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public UserResponse create(UserRequest dto) {
        return toResponse(service.create(toDto(dto)));
    }

    @Override
//    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public UserResponse get(UUID id) {
        return toResponse(service.get(id));
    }

    @Override
//    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public List<UserResponse> list() {
        return service.list().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
//    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public UserResponse update(UUID id, UserRequest dto) {
        return toResponse(service.update(id, toDto(dto)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(UUID id) {
        service.delete(id);
    }

    private User toDto(UserRequest request) {
        return new User(
                request.getId(),
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate()
        );
    }

    private UserResponse toResponse(User dto) {
        UserResponse response = new UserResponse();
        response.setId(dto.id());
        response.setFirstName(dto.firstName());
        response.setLastName(dto.lastName());
        return response;
    }
}
