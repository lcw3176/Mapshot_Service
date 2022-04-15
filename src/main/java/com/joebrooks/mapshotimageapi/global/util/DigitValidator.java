package com.joebrooks.mapshotimageapi.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DigitValidator {

    public boolean isDigit(String value){
        for(char i : value.toCharArray()){
            if(!Character.isDigit(i)){
                return false;
            }
        }

        return true;
    }
}
