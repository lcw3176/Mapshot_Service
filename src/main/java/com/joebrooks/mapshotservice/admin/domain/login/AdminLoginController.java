package com.joebrooks.mapshotservice.admin.domain.login;

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
        return "fragment/admin/admin-login";
    }


    @PostMapping
    public String tryLogin(AdminLoginRequest adminLoginRequest, HttpSession session) throws NoSuchAlgorithmException {
        boolean result = adminService.login(adminLoginRequest);
        session.setAttribute("admin", result);

        return "redirect:/admin/board";
    }

}
