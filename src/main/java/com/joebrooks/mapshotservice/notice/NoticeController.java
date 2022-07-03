package com.joebrooks.mapshotservice.notice;

import com.joebrooks.mapshotservice.global.util.DigitValidator;
import com.joebrooks.mapshotservice.global.util.PageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showNotices(@RequestParam(name = "post", required = false) Optional<String> post,
                              @RequestParam(name = "page", required = false, defaultValue = "1") String page,
                                 Model model){

        if(post.isPresent() && DigitValidator.isDigit(post.get())){
            long postNum = Long.parseLong(post.get());

            NoticeEntity noticeEntity = noticeService.getPost(postNum)
                    .orElseThrow(() -> {
                        throw new IllegalStateException("잘못된 공지사항 접근");
                    });

            model.addAttribute("post", noticeEntity);

            return "fragment/notice/notice-detail";
        } else {
            int nowPage = DigitValidator.isDigit(page) ? Integer.parseInt(page) : 1;
            PageGenerator.init(nowPage, noticeService.getSize());
            
            model.addAttribute("posts", noticeService.getPosts(PageGenerator.getNowPage() - 1));
            model.addAttribute("startPage", PageGenerator.getStartPage());
            model.addAttribute("lastPage", PageGenerator.getLastPage());
            model.addAttribute("nowPage", PageGenerator.getNowPage());

            return "fragment/notice/notice";
        }

    }
}