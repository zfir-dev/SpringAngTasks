package dev.zfir.springangtasks.task;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateRequest(
        @NotBlank String title,
        String description
) {}
