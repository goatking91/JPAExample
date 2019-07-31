package io.lfin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UsersDto {

    /**
     * 유저 Dto
     */
    @Getter
    @Setter
    @ToString
    public static class User {

        private String name;

        private Long age;
    }
}
