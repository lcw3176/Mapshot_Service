package com.joebrooks.mapshotservice.admin.notice.edit;

import com.joebrooks.mapshotservice.notice.NoticeType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AdminEditNoticeRequest {

    private Long id;

    @NotBlank(message = "제목을 입력해 주세요")
    private String title;

    private NoticeType noticeType;

    @NotBlank(message = "내용을 입력해 주세요")
    private String content;

}

