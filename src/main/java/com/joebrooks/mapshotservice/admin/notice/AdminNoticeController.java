package com.joebrooks.mapshotservice.admin.notice;

import com.joebrooks.mapshotservice.global.util.PageGenerator;
import com.joebrooks.mapshotservice.notice.NoticeEntity;
import com.joebrooks.mapshotservice.notice.NoticeService;
import com.joebrooks.mapshotservice.notice.NoticeType;
import com.joebrooks.mapshotservice.notice.PageResponse;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String showAdminNoticePage(@Positive @RequestParam(name = "page", required = false, defaultValue = "1") int requestPage,
                                Model model){
        int offset = 1;
        int index = requestPage - offset;
        Page<NoticeEntity> pages =  noticeService.getPosts(index);

        int startPage = PageGenerator.getStartPage(requestPage);
        int lastPage = PageGenerator.getLastPage(requestPage, pages.getTotalElements());

        Pageable pageable = pages.getPageable();

        model.addAttribute("posts", pages);
        model.addAttribute("pageResponse", PageResponse.builder()
                .startPage(startPage)
                .lastPage(lastPage)
                .nowPage(requestPage)
                .previousPage(pageable.previousOrFirst().getPageNumber() + offset)
                .nextPage(pageable.next().getPageNumber() + offset)
                .hasNext(pages.hasNext())
                .hasPrevious(pages.hasPrevious())
                .build());

        return "fragment/admin/admin-notice";
    }

    @GetMapping("/register")
    public String showRegisterPostPage(Model model){
        model.addAttribute("types", NoticeType.values());
        model.addAttribute("registerNoticeRequest", AdminRegisterNoticeRequest.builder().build());

        return "fragment/admin/admin-register-notice";
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

            return "fragment/admin/admin-register-notice";
        }

        noticeService.addPost(NoticeEntity.builder()
                .title(noticeRequest.getTitle())
                .noticeType(noticeRequest.getNoticeType())
                .content(noticeRequest.getContent())
                .build());

        return "redirect:/admin/notice";
    }

    @GetMapping("/edit")
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

    @PostMapping("/edit")
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

        noticeService.editPost(
                NoticeEntity.builder()
                        .id(noticeRequest.getId())
                        .noticeType(noticeRequest.getNoticeType())
                        .content(noticeRequest.getContent())
                        .title(noticeRequest.getTitle())
                        .build()
        );

        return "redirect:/admin/notice";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam(name = "post", required = false) Optional<Long> post) {

        long postNumber = post.orElseThrow(() -> {
            throw new IllegalArgumentException("해당되는 게시물이 없습니다.");
        });

        noticeService.removePost(postNumber);

        return "redirect:/admin/notice";
    }


}
