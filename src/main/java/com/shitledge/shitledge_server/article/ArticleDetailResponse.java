package com.shitledge.shitledge_server.article;

import java.time.LocalDate;

public record ArticleDetailResponse(
        Long id,
        String title,
        String author,
        String email,
        LocalDate publishedDate,
        String summary,
        String body
) {
    static ArticleDetailResponse from(Article article) {
        return new ArticleDetailResponse(
                article.getId(),
                article.getTitle(),
                article.getAuthor(),
                article.getEmail(),
                article.getPublishedDate(),
                article.getSummary(),
                article.getBody()
        );
    }
}
