package com.joebrooks.mapshotimageapi.presentation;

import com.joebrooks.mapshotimageapi.global.util.DigitValidationUtil;
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

    @GetMapping
    public String showNotices(@RequestParam(name = "post", required = false) Optional<Long> post,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer nowPage,
                                 Model model){

        if(post.isPresent()){
            NoticeEntity noticeEntity = noticeService.getPost(post.get())
                    .orElseThrow(() -> {
                        throw new IllegalStateException("잘못된 공지사항 접근");
                    });

            model.addAttribute("post", noticeEntity);

            return "fragment/notice/notice-detail";
        } else {
            int totalPage = Math.max((int) (noticeService.getSize() / 10), 1);

            nowPage = nowPage < 1 ? 1 : nowPage;
            nowPage = nowPage > totalPage ? totalPage : nowPage;

            int startPage = Math.max((nowPage) / 10, 1);
            int lastPage = Math.min(startPage + 10, totalPage);

            model.addAttribute("posts", noticeService.getPosts(nowPage - 1));
            model.addAttribute("startPage", startPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("nowPage", nowPage);

            return "fragment/notice/notice";
        }

    }
}