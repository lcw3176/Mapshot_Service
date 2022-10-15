package com.joebrooks.mapshotservice.notice;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Positive;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{page}")
    public String showNoticeList(@Positive @PathVariable(value = "page") int requestPage, Model model) {

        if(!PageGenerator.isValidate(requestPage, noticeService.getSize())){
            requestPage = 1;
        }

        int startPage = PageGenerator.getStartPage(requestPage);
        int lastPage = PageGenerator.getLastPage(requestPage, noticeService.getSize());

        model.addAttribute("posts", noticeService.getPosts(requestPage - 1));
        model.addAttribute("pageResponse", PageResponse.builder()
                .startPage(startPage)
                .lastPage(lastPage)
                .nowPage(requestPage)
                .nextPage(PageGenerator.getNextPage(lastPage, noticeService.getSize()))
                .previousPage(PageGenerator.getPreviousPage(startPage))
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