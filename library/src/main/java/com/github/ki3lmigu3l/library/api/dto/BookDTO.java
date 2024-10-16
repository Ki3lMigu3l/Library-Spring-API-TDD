package com.github.ki3lmigu3l.library.api.dto;

import lombok.Builder;

@Builder
public record BookDTO(
        Long id,
        String title,
        String author,
        String isbn
) {
}
