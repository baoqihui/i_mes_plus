package com.rh.i_mes_plus.util;
import lombok.Data;

@Data
public class User {
    private String name;
    private String gender;
    private School school;
    @Data
    public static class School {
        private String scName;
        private String adress;
    }
}