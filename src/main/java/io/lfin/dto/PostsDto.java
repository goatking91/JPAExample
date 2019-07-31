package io.lfin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PostsDto {
    /**
     * 작성 글 Dto
     */
    @Getter
    @Setter
    @ToString
    public static class Post {

        private String title;

        private String content;

        private Long userId;

        private String userName;
    }
}
