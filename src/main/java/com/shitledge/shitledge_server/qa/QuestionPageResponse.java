package com.shitledge.shitledge_server.qa;

import java.util.List;

public record QuestionPageResponse(
        List<QuestionSummaryResponse> items,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {
}
