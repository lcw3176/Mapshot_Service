package com.joebrooks.mapshotimageapi.admin.dashboard;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeEntity;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board")
public class AdminDashboardController {

    private final NoticeService noticeService;

    @GetMapping
    public String showAdminPage(Model model){
        Page<NoticeEntity> posts = noticeService.getPosts(0);

        model.addAttribute("todayUser", 1200);
        model.addAttribute("totalUser", 24000);
        model.addAttribute("posts", posts);

        return "fragment/admin/admin-board";
    }

}
