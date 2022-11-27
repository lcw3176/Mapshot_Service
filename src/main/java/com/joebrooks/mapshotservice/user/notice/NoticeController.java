package com.joebrooks.mapshotservice.user.notice;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private static final int OFFSET = 1;

    @GetMapping("/{page}")
    public String showNoticeList(@Positive @PathVariable(value = "page") int requestPage, Model model) {
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

        return "fragment/notice/notice";
    }

    @GetMapping
    public String showPost(@Positive @RequestParam(name = "post", required = false) Long postNumber, Model model){

        if(postNumber == null){
            return "redirect:/notice/1";
        }

        NoticeEntity noticeEntity = noticeService.getPost(postNumber);
        model.addAttribute("post", noticeEntity);

        return "fragment/notice/notice-detail";
    }

}