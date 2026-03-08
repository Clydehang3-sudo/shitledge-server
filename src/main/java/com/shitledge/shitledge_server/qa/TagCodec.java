package com.shitledge.shitledge_server.qa;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TagCodec {

    private TagCodec() {
    }

    public static List<String> split(String raw) {
        if (raw == null || raw.isBlank()) {
            return List.of();
        }

        return Arrays.stream(raw.split("[,，]"))
                .map(String::trim)
                .filter(tag -> !tag.isBlank())
                .distinct()
                .limit(8)
                .toList();
    }

    public static String join(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }

        return tags.stream()
                .map(String::trim)
                .filter(tag -> !tag.isBlank())
                .distinct()
                .limit(8)
                .collect(Collectors.joining(","));
    }
}
