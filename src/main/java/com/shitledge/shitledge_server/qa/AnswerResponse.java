package com.shitledge.shitledge_server.qa;

import java.time.LocalDateTime;

public record AnswerResponse(
        Long id,
        Long questionId,
        String authorName,
        String content,
        LocalDateTime createdAt,
        int likes
) {
}
