package com.shuo747.ilnpu.entity.model;

public class WxModel {


    private String code;
    private String key;
    private String iv;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    @Override
    public String toString() {
        return "WxModel{" +
                "code='" + code + '\'' +
                ", key='" + key + '\'' +
                ", iv='" + iv + '\'' +
                '}';
    }
}
