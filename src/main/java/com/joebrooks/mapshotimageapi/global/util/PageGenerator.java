package com.joebrooks.mapshotimageapi.global.util;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageGenerator {

    private final NoticeService noticeService;

    private int getTotalPage(){
        return Math.max((int) (noticeService.getSize() / 10), 1);
    }

    public int getNowPage(int nowPage){
        nowPage = Math.max(nowPage, 1);
        nowPage = Math.min(nowPage, getTotalPage());

        return nowPage;
    }


    public int getStartPage(int nowPage){
        return Math.max((getNowPage(nowPage)) / 10, 1);
    }

    public int getLastPage(int nowPage){
        return Math.min(getStartPage(nowPage) + 10, getTotalPage());
    }

}
