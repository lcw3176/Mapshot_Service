package com.joebrooks.mapshotimageapi.presentation;

import com.joebrooks.mapshotimageapi.global.util.DigitValidator;
import com.joebrooks.mapshotimageapi.global.util.PageGenerator;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeEntity;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
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
    private final PageGenerator pageGenerator;

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
            pageGenerator.init(nowPage);
            
            model.addAttribute("posts", noticeService.getPosts(pageGenerator.getNowPage() - 1));
            model.addAttribute("startPage", pageGenerator.getStartPage());
            model.addAttribute("lastPage", pageGenerator.getLastPage());
            model.addAttribute("nowPage", pageGenerator.getNowPage());

            return "fragment/notice/notice";
        }

    }
}