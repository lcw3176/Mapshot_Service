package com.joebrooks.mapshotservice.user.domain.notice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;


    @Test
    void 존재하지_않는_게시글_조회시_예외_발생(){
        assertThrows(IllegalArgumentException.class, () -> {
            noticeService.getPost(-1L);
        }) ;
    }

    @Test
    void 존재하지_않는_게시글_수정시_예외_발생(){
        assertThrows(IllegalArgumentException.class, () -> {
            noticeService.editPost(NoticeEntity.builder()
                    .id(-1L)
                    .noticeType(NoticeType.FIX)
                    .content("hello")
                    .title("good")
                    .build());
        }) ;
    }
    
}