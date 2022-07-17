package com.joebrooks.mapshotservice.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageGenerator {

    public boolean isValidate(int nowPage, long totalElementSize){
        long maxPage = totalElementSize / 10 + 1;

        return maxPage >= nowPage;
    }

    public int getStartPage(int nowPage){
        return Math.max(nowPage / 10, 1);
    }

    public int getLastPage(int nowPage, long totalElementSize){
        return (int) Math.min((nowPage / 10 + 1) * 10, ((totalElementSize - 1) / 10) + 1);
    }


}
