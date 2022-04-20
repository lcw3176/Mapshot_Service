package com.joebrooks.mapshotimageapi.global.util;

import com.joebrooks.mapshotimageapi.repository.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageGenerator {

    private final NoticeService noticeService;
    private int nowPage;

    public void init(int nowPage){
        nowPage = Math.max(nowPage, 1);
        nowPage = Math.min(nowPage, getTotalPage());
        this.nowPage = nowPage;
    }

    private int getTotalPage(){
        return (int) (((noticeService.getSize() - 1) / 10) + 1);
    }

    public int getNowPage(){
        return this.nowPage;
    }


    public int getStartPage(){
        return Math.max(nowPage / 10, 1);
    }

    public int getLastPage(){
        return Math.min(((nowPage / 10) + 1) * 10, getTotalPage());
    }

}
