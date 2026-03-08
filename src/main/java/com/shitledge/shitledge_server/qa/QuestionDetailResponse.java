package com.shitledge.shitledge_server.qa;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionDetailResponse(
        Long id,
        String title,
        String description,
        String authorName,
        LocalDateTime createdAt,
        List<String> tags,
        long answerCount
) {
}
