package com.joebrooks.mapshotservice.admin.notice.delete;

import com.joebrooks.mapshotservice.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice/delete")
public class AdminDeleteNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String adjustPost(@RequestParam(name = "post", required = false) Optional<Long> post) {

        long postNumber = post.orElseThrow(() -> {
            throw new RuntimeException("no such post");
        });

        noticeService.removePost(postNumber);

        return "redirect:/admin/board";
    }
}
