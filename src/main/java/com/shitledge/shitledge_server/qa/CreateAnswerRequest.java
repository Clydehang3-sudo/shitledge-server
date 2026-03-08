package com.shitledge.shitledge_server.qa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAnswerRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = 80, message = "must be 80 characters or fewer")
        String authorName,

        @NotBlank(message = "must not be blank")
        @Size(min = 4, max = 3200, message = "must be between 4 and 3200 characters")
        String content
) {
}
