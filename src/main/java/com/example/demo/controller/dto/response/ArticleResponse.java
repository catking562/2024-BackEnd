package com.example.demo.controller.dto.response;

import java.time.LocalDateTime;

import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record ArticleResponse(
    Long id,
    Long authorId,
    Long boardId,
    String title,
    String description,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdDate,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime modifiedDate
) {

    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
            article.getId(),
            article.getAuthor().getId(),
            article.getBoard().getId(),
            article.getTitle(),
            article.getContent(),
            article.getCreatedAt(),
            article.getModifiedAt()
        );
    }
}
