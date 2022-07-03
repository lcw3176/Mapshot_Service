package com.joebrooks.mapshotservice.admin.notice.register;

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

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice/register")
public class AdminRegisterNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showRegisterPostPage(Model model){
        model.addAttribute("types", NoticeType.values());
        model.addAttribute("registerNoticeRequest", AdminRegisterNoticeRequest.builder().build());

        return "fragment/admin/admin-register-notice";
    }

    @PostMapping
    public String registerPost(Model model, @Valid AdminRegisterNoticeRequest noticeRequest, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("registerNoticeRequest", noticeRequest);
            model.addAttribute("types", NoticeType.values());

            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-register-notice";
        }

        noticeService.addPost(NoticeEntity.builder()
                .title(noticeRequest.getTitle())
                .noticeType(noticeRequest.getNoticeType())
                .content(noticeRequest.getContent())
                .build());

        return "redirect:/admin/board";
    }
}
