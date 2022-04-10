package com.joebrooks.mapshotimageapi.admin.dashboard;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminCreateNoticeRequest {
    private String title;
    private NoticeType noticeType;
    private String content;

}
