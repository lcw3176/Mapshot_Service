package com.joebrooks.mapshotservice.admin.domain.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class AdminDashboardController {


    @GetMapping
    public String showAdminPage(){
        return "fragment/admin/admin-board";
    }


}
