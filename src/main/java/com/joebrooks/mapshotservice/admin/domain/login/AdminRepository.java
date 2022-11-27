package com.joebrooks.mapshotservice.admin.domain.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    boolean existsByNickNameAndPassword(String id, String pw);

}
