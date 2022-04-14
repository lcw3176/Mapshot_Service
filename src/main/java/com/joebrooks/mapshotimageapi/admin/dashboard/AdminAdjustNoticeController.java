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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board")
public class AdminAdjustNoticeController {

    private final NoticeService noticeService;

    @GetMapping("/register")
    public String showRegisterPostPage(Model model){
        model.addAttribute("types", NoticeType.values());
        model.addAttribute("registerNoticeRequest", AdminRegisterNoticeRequest.builder().build());

        return "fragment/admin/admin-register-post";
    }

    @PostMapping("/register")
    public String registerPost(Model model, @Valid AdminRegisterNoticeRequest noticeRequest, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("registerNoticeRequest", noticeRequest);
            model.addAttribute("types", NoticeType.values());

            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-register-post";
        }

        noticeService.addPost(NoticeEntity.builder()
                .title(noticeRequest.getTitle())
                .noticeType(noticeRequest.getNoticeType())
                .content(noticeRequest.getContent())
                .build());

        return "redirect:/admin/board";
    }

    @GetMapping("/edit")
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

        return "fragment/admin/admin-edit-post";
    }

    @PostMapping("/edit")
    public String editPost(Model model, @Valid AdminEditNoticeRequest noticeRequest, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute("editNoticeRequest", noticeRequest);
            model.addAttribute("types", NoticeType.values());

            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                model.addAttribute(validKeyName, error.getDefaultMessage());
            }

            return "fragment/admin/admin-edit-post";
        }

        NoticeEntity noticeEntity = noticeService.getPost(noticeRequest.getId())
                .orElseThrow(() -> {
                    throw new RuntimeException("못찾음");
                });

        noticeEntity.changeNoticeType(noticeRequest.getNoticeType());
        noticeEntity.changeContent(noticeRequest.getContent());
        noticeEntity.changeTitle(noticeRequest.getTitle());

        noticeService.save(noticeEntity);

        return "redirect:/admin/board";
    }


    @GetMapping("/delete")
    public String adjustPost(@RequestParam(name = "post", required = false) Optional<Long> post) {

        long postNumber = post.orElseThrow(() -> {
            throw new RuntimeException("no such post");
        });

        noticeService.removePost(postNumber);

        return "redirect:/admin/board";
    }


}
