package com.joebrooks.mapshotservice.notice;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse {

    private int startPage;
    private int lastPage;
    private int nowPage;
    private int nextPage;
    private int previousPage;
}
