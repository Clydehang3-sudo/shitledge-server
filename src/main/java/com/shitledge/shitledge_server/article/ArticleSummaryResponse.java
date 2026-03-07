package com.shitledge.shitledge_server.article;

import java.time.LocalDate;

public record ArticleSummaryResponse(
        Long id,
        String title,
        String author,
        LocalDate publishedDate,
        String summary
) {
    static ArticleSummaryResponse from(Article article) {
        return new ArticleSummaryResponse(
                article.getId(),
                article.getTitle(),
                article.getAuthor(),
                article.getPublishedDate(),
                article.getSummary()
        );
    }
}
