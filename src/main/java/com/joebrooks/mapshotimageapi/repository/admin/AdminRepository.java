package com.joebrooks.mapshotimageapi.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByNickNameAndPassword(String id, String pw);

}
