package com.joebrooks.mapshotservice.user.domain.notice;

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
    private boolean hasNext;
    private boolean hasPrevious;
}
