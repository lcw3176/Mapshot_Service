package com.joebrooks.mapshotimageapi.repository.admin;

import com.joebrooks.mapshotimageapi.global.encrypto.SHA256;
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

    public Optional<AdminEntity> login(String id, String pw) throws NoSuchAlgorithmException {
        return adminRepository.findByNickNameAndPassword(SHA256.encrypt(id), SHA256.encrypt(pw));
    }
}
