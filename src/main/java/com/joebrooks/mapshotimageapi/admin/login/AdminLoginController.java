package com.joebrooks.mapshotimageapi.admin.login;

import com.joebrooks.mapshotimageapi.repository.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/login")
public class AdminLoginController {

    private final AdminService adminService;

    @GetMapping
    public String showLoginPage(Model model, AdminLoginRequest adminLoginRequest){
        return "admin-login";
    }


    @PostMapping
    public String tryLogin(AdminLoginRequest adminLoginRequest, HttpSession session) throws NoSuchAlgorithmException {
        adminService.login(adminLoginRequest)
                    .orElseThrow(() -> {
                        throw new AdminLoginException(adminLoginRequest);
                    });

        session.setAttribute("admin", true);

        return "redirect:/admin/board";
    }

}
