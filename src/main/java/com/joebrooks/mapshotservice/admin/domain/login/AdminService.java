package com.joebrooks.mapshotservice.admin.domain.login;

import com.joebrooks.mapshotservice.admin.util.SHA256;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public boolean login(AdminLoginRequest adminRequest) throws NoSuchAlgorithmException {
        return adminRepository.existsByNickNameAndPassword(
                SHA256.encrypt(adminRequest.getNickName()),
                SHA256.encrypt(adminRequest.getPassword()));
    }
}
