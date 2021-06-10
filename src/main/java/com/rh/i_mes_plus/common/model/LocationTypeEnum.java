package com.rh.i_mes_plus.common.model;

public enum LocationTypeEnum {
    MSD("msd", "湿敏"),;
    private String ltCode;

    private String ltName;

    LocationTypeEnum(String ltCode, String ltName) {
        this.ltCode = ltCode;
        this.ltName = ltName;
    }

    public String getLtCode() {
        return ltCode;
    }

    public void setLtCode(String ltCode) {
        this.ltCode = ltCode;
    }

    public String getLtName() {
        return ltName;
    }

    public void setLtName(String ltName) {
        this.ltName = ltName;
    }
}
