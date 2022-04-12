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
    public String showNoticeList(@RequestParam(name = "post", required = false) Optional<String> post,
                                 Model model){

        if(post.isPresent() && DigitValidationUtil.isDigit(post.get())){
            NoticeEntity noticeEntity = noticeService.getPost(Long.parseLong(post.get()))
                    .orElseThrow(() -> {
                        throw new IllegalStateException("잘못된 공지사항 접근");
                    });

            model.addAttribute("post", noticeEntity);
            
            return "notice-detail";
        } else {
            model.addAttribute("posts", noticeService.getPosts(0));

            return "notice";
        }

    }
}
