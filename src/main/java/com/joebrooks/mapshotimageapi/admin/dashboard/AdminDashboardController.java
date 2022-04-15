package com.joebrooks.mapshotimageapi.admin.dashboard;

import com.joebrooks.mapshotimageapi.global.util.PageGenerator;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board")
public class AdminDashboardController {

    private final NoticeService noticeService;
    private final PageGenerator pageGenerator;

    @GetMapping
    public String showAdminPage(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer nowPage){

        model.addAttribute("posts", noticeService.getPosts(pageGenerator.getNowPage(nowPage) - 1));
        model.addAttribute("startPage", pageGenerator.getStartPage(nowPage));
        model.addAttribute("lastPage", pageGenerator.getLastPage(nowPage));
        model.addAttribute("nowPage", pageGenerator.getNowPage(nowPage));

        model.addAttribute("todayUser", 1200);
        model.addAttribute("totalUser", 24000);

        return "fragment/admin/admin-board";
    }

}
