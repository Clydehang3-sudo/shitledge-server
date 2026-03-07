package com.shitledge.shitledge_server.article;

import java.util.List;

public record ArticlePageResponse(
        List<ArticleSummaryResponse> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {
}
