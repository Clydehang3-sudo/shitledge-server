package com.shitledge.shitledge_server.qa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateQuestionRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = 160, message = "must be 160 characters or fewer")
        String title,

        @NotBlank(message = "must not be blank")
        @Size(max = 2400, message = "must be 2400 characters or fewer")
        String description,

        @NotBlank(message = "must not be blank")
        @Size(max = 80, message = "must be 80 characters or fewer")
        String authorName,

        @Size(max = 320, message = "must be 320 characters or fewer")
        String tags
) {
}
