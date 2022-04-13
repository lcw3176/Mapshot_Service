package com.joebrooks.mapshotimageapi.admin.dashboard;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeEntity;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
import com.joebrooks.mapshotimageapi.repository.notice.NoticeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/create")
public class AdminCreateNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showCreatePostPage(Model model, AdminCreateNoticeRequest adminCreateNoticeRequest){
        model.addAttribute("noticeType", NoticeType.values());

        return "fragment/admin/admin-create-post";
    }

    @PostMapping
    public String registerPost(Model model, @Valid AdminCreateNoticeRequest noticeRequest, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("adminCreateNoticeRequest", noticeRequest);
            model.addAttribute("noticeType", NoticeType.values());

            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-create-post";
        }

        noticeService.addPost(NoticeEntity.builder()
                .title(noticeRequest.getTitle())
                .noticeType(noticeRequest.getNoticeType())
                .content(noticeRequest.getContent())
                .build());

        return "redirect:/admin/board";
    }
}
