package com.joebrooks.mapshotservice.admin.dashboard;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import com.joebrooks.mapshotservice.notice.NoticeService;
import com.joebrooks.mapshotservice.notice.PageResponse;
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
            page = 1;
        }

        int startPage = PageGenerator.getStartPage(page);
        int lastPage = PageGenerator.getLastPage(page, noticeService.getSize());

        model.addAttribute("posts", noticeService.getPosts(page - 1));
        model.addAttribute("pageResponse", PageResponse.builder()
                .startPage(startPage)
                .lastPage(lastPage)
                .nowPage(page)
                .nextPage(PageGenerator.getNextPage(lastPage, noticeService.getSize()))
                .previousPage(PageGenerator.getPreviousPage(startPage))
                .build());

        return "fragment/admin/admin-board";
    }


}
