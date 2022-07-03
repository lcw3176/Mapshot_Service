package com.joebrooks.mapshotservice.admin.notice.edit;

import com.joebrooks.mapshotservice.notice.NoticeEntity;
import com.joebrooks.mapshotservice.notice.NoticeService;
import com.joebrooks.mapshotservice.notice.NoticeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice/edit")
public class AdminEditNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showEditPostPage(@RequestParam(name = "post", required = false) Optional<Long> post,
                                   Model model){

        long postNumber = post.orElseThrow(() -> {
            throw new RuntimeException("no such post");
        });

        NoticeEntity noticeEntity = noticeService.getPost(postNumber).orElseThrow(() -> {
            throw new RuntimeException("no such post");
        });

        model.addAttribute("editNoticeRequest",  AdminEditNoticeRequest.builder()
                .id(noticeEntity.getId())
                .title(noticeEntity.getTitle())
                .content(noticeEntity.getContent())
                .noticeType(noticeEntity.getNoticeType())
                .build());

        model.addAttribute("types", NoticeType.values());

        return "fragment/admin/admin-edit-notice";
    }

    @PostMapping
    public String editPost(Model model, @Valid AdminEditNoticeRequest noticeRequest, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("editNoticeRequest", noticeRequest);
            model.addAttribute("types", NoticeType.values());

            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-edit-notice";
        }

        NoticeEntity noticeEntity = noticeService.getPost(noticeRequest.getId())
                .orElseThrow(() -> {
                    throw new RuntimeException("no such post");
                });

        noticeEntity.changeNoticeType(noticeRequest.getNoticeType());
        noticeEntity.changeContent(noticeRequest.getContent());
        noticeEntity.changeTitle(noticeRequest.getTitle());

        noticeService.editPost(noticeEntity);

        return "redirect:/admin/board";
    }
}
