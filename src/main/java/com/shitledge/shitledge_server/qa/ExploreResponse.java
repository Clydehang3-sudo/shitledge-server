package com.shitledge.shitledge_server.qa;

import java.util.List;

public record ExploreResponse(
        List<QuestionSummaryResponse> hottest,
        List<QuestionSummaryResponse> latest,
        List<QuestionSummaryResponse> random,
        List<String> tags
) {
}
