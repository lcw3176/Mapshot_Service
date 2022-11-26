package com.joebrooks.mapshotservice.admin.dashboard;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import com.joebrooks.mapshotservice.notice.NoticeEntity;
import com.joebrooks.mapshotservice.notice.NoticeService;
import com.joebrooks.mapshotservice.notice.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private static final int OFFSET = 1;

    @GetMapping
    public String showAdminPage(@Positive @RequestParam(name = "page", required = false, defaultValue = "1") int requestPage,
                                Model model){

        int index = requestPage - OFFSET;
        Page<NoticeEntity> pages =  noticeService.getPosts(index);

        int startPage = PageGenerator.getStartPage(requestPage);
        int lastPage = PageGenerator.getLastPage(requestPage, pages.getTotalElements());

        Pageable pageable = pages.getPageable();

        model.addAttribute("posts", pages);
        model.addAttribute("pageResponse", PageResponse.builder()
                .startPage(startPage)
                .lastPage(lastPage)
                .nowPage(requestPage)
                .previousPage(pageable.previousOrFirst().getPageNumber() + OFFSET)
                .nextPage(pageable.next().getPageNumber() + OFFSET)
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build());

        return "fragment/admin/admin-board";
    }


}
