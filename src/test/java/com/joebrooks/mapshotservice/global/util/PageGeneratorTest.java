package com.joebrooks.mapshotservice.global.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PageGeneratorTest {

    @Test
    void 유효한_페이지_테스트(){
        assertEquals(false, PageGenerator.isValidate(2, 9));
        assertEquals(true, PageGenerator.isValidate(2, 11));
    }
    
    @Test
    void 첫_페이지_테스트(){
        for(int i = 1; i <= 10; i++){
            assertEquals(1, PageGenerator.getStartPage(i));
        }

        for(int i = 11; i <= 20; i++){
            assertEquals(11, PageGenerator.getStartPage(i));
        }
    }

    @Test
    void 마지막_페이지_테스트(){
        for(int i = 1; i <= 10; i++){
            assertEquals(10, PageGenerator.getLastPage(i, 1000));
        }

        for(int i = 11; i <= 20; i++){
            assertEquals(20, PageGenerator.getLastPage(i, 1000));
        }
    }
}