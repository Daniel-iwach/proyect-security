package com.securityapp.spring_security.persistence.repository;

import com.securityapp.spring_security.persistence.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

        Optional<UserEntity> findUserEntityByUsername(String username);

        boolean existsByEmail(String email);

        @Transactional
        void deleteUserEntityByUsername(String username);
}
