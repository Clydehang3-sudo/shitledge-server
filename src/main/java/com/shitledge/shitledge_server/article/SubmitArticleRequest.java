package com.shitledge.shitledge_server.article;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubmitArticleRequest(
        @NotBlank @Size(max = 180) String title,
        @NotBlank @Size(max = 120) String author,
        @NotBlank @Email @Size(max = 200) String email,
        @NotBlank @Size(max = 600) String summary,
        @NotBlank String body
) {
}
