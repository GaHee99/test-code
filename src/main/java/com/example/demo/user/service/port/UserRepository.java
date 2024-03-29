package com.example.demo.user.service.port;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.infrastructure.UserEntity;
import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus active);

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus active);
}
