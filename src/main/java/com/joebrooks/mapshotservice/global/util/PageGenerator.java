package com.joebrooks.mapshotservice.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageGenerator {

    private int nowPage;
    private long totalPageSize;

    public void init(int nowUserPage, long totalSize){
        totalPageSize = totalSize;

        nowUserPage = Math.max(nowUserPage, 1);
        nowUserPage = Math.min(nowUserPage, getTotalPage());
        nowPage = nowUserPage;
    }

    private int getTotalPage(){
        return (int) (((totalPageSize - 1) / 10) + 1);
    }

    public int getNowPage(){
        return nowPage;
    }


    public int getStartPage(){
        return Math.max(nowPage / 10, 1);
    }

    public int getLastPage(){
        return Math.min(((nowPage / 10) + 1) * 10, getTotalPage());
    }

}
