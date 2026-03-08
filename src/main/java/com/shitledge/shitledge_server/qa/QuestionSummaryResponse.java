package com.shitledge.shitledge_server.qa;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionSummaryResponse(
        Long id,
        String title,
        String descriptionSnippet,
        String authorName,
        LocalDateTime createdAt,
        List<String> tags,
        long answerCount,
        String latestAnswerSnippet
) {
}
