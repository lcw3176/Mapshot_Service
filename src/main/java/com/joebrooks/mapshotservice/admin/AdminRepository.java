package com.joebrooks.mapshotservice.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByNickNameAndPassword(String id, String pw);

}
