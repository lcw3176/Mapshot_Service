package com.joebrooks.mapshotservice.admin.notice.edit;

import com.joebrooks.mapshotservice.notice.NoticeEntity;
import com.joebrooks.mapshotservice.notice.NoticeService;
import com.joebrooks.mapshotservice.notice.NoticeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/notice/edit")
public class AdminEditNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showEditPostPage(@Positive @RequestParam(name = "post") Long post, Model model){
        NoticeEntity noticeEntity = noticeService.getPost(post);

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
    public String editPost(Model model, @Valid AdminEditNoticeRequest noticeRequest, BindingResult result){

        if(result.hasErrors()){
            model.addAttribute("editNoticeRequest", noticeRequest);
            model.addAttribute("types", NoticeType.values());

            for (FieldError error : result.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-edit-notice";
        }

        noticeService.editPost(noticeRequest);

        return "redirect:/admin/board";
    }
}
