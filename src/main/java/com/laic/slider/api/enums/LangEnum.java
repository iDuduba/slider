package com.laic.slider.api.enums;

/**
 * Created by duduba on 16/5/20.
 */
public enum LangEnum {
    CN("cn"),JP("jp");

    private String code;

    LangEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
