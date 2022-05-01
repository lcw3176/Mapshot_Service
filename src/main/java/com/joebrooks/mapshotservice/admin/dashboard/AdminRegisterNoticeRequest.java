package com.joebrooks.mapshotservice.admin.dashboard;

import com.joebrooks.mapshotservice.repository.notice.NoticeType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AdminRegisterNoticeRequest {

    @NotEmpty(message = "제목을 입력해 주세요")
    private String title;

    private NoticeType noticeType;

    @NotEmpty(message = "내용을 입력해 주세요")
    private String content;

}
