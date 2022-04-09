package com.joebrooks.mapshotimageapi.admin;

import com.joebrooks.mapshotimageapi.repository.admin.AdminEntity;
import com.joebrooks.mapshotimageapi.repository.admin.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/board")
    public String showAdminPage(){

        return "admin-board";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "admin-login";
    }


    @PostMapping("/login")
    public String tryLogin(AdminRequest adminRequest, HttpSession session) throws NoSuchAlgorithmException {
        AdminEntity adminEntity = adminService.login(adminRequest.getNickName(), adminRequest.getPassword())
                                    .orElseThrow(() -> {
                                        throw new AdminLoginException("hello", "hello");
                                    });

        session.setAttribute("admin", adminEntity.getNickName());

        return "admin-board";
    }

}
