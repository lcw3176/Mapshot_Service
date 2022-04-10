package com.joebrooks.mapshotimageapi.admin.dashboard;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class AdminDashboardController {

    @GetMapping
    public String showAdminPage(Model model){
        model.addAttribute("todayUser", 1200);
        model.addAttribute("totalUser", 24000);

        return "admin-board";
    }

    @GetMapping("/create")
    public String showCreatePostPage(Model model, AdminCreateNoticeRequest adminCreateNoticeRequest){
        model.addAttribute("noticeType", NoticeType.values());

        return "admin-create-post";
    }
}
