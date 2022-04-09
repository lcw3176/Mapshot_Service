package com.joebrooks.mapshotimageapi.presentation;

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
@RequestMapping("/notice/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String showLoginPage(){
        return "admin-login";
    }


    @PostMapping
    public String tryLogin(AdminRequest adminRequest, HttpSession session) throws Exception {
        adminService.login(adminRequest.getNickName(), adminRequest.getPassword())
                .orElseThrow(IllegalAccessException::new);

        return "redirect:/notice";
    }

    @Data
    private class AdminRequest{
        private String nickName;
        private String password;
    }

}
