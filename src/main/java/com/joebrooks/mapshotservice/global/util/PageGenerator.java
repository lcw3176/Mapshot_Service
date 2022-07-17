package com.joebrooks.mapshotservice.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageGenerator {

    public boolean isValidate(int requestPage, long totalElementSize){
        long maxPage = totalElementSize / 10 + 1;

        return maxPage >= requestPage;
    }

    public int getStartPage(int nowPage){
        return Math.max((nowPage - 1) / 10 * 10 + 1, 1);
    }

    public int getLastPage(int nowPage, long totalElementSize){
        if(nowPage % 10 == 0){
            nowPage -= 1;
        }

        return (int) Math.min((nowPage / 10 + 1) * 10, ((totalElementSize - 1) / 10) + 1);
    }


}
