package com.joebrooks.mapshotservice.repository.admin;

import com.joebrooks.mapshotservice.admin.login.AdminLoginRequest;
import com.joebrooks.mapshotservice.global.encrypto.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<AdminEntity> login(AdminLoginRequest adminRequest) throws NoSuchAlgorithmException {
        return adminRepository.findByNickNameAndPassword(
                SHA256.encrypt(adminRequest.getNickName()),
                SHA256.encrypt(adminRequest.getPassword()));
    }
}
