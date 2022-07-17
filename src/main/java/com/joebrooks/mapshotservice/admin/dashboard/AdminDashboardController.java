package com.joebrooks.mapshotservice.admin.dashboard;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import com.joebrooks.mapshotservice.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Positive;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board")
public class AdminDashboardController {

    private final NoticeService noticeService;

    @GetMapping
    public String showAdminPage(@Positive @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                Model model){

        if(!PageGenerator.isValidate(page, noticeService.getSize())){
            throw new IllegalStateException("잘못된 공지사항 접근");
        }


        model.addAttribute("posts", noticeService.getPosts(page - 1));
        model.addAttribute("startPage", PageGenerator.getStartPage(page));
        model.addAttribute("lastPage", PageGenerator.getLastPage(page, noticeService.getSize()));
        model.addAttribute("nowPage", page);


        return "fragment/admin/admin-board";
    }


}
