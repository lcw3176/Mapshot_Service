package com.joebrooks.mapshotservice.admin.dashboard;

import com.joebrooks.mapshotservice.global.util.DigitValidator;
import com.joebrooks.mapshotservice.global.util.PageGenerator;
import com.joebrooks.mapshotservice.repository.notice.NoticeService;
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
    public String showAdminPage(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") String page){
        int nowPage = DigitValidator.isDigit(page) ? Integer.parseInt(page) : 1;
        pageGenerator.init(nowPage);
        int index = pageGenerator.getNowPage() - 1;

        model.addAttribute("posts", noticeService.getPosts(index));
        model.addAttribute("startPage", pageGenerator.getStartPage());
        model.addAttribute("lastPage", pageGenerator.getLastPage());
        model.addAttribute("nowPage", pageGenerator.getNowPage());

        return "fragment/admin/admin-board";
    }

}
