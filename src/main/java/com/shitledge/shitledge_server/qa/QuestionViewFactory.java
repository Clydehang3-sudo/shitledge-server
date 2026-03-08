package com.shitledge.shitledge_server.qa;

import org.springframework.stereotype.Component;

@Component
public class QuestionViewFactory {

    private final AnswerRepository answerRepository;

    public QuestionViewFactory(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public QuestionSummaryResponse toSummary(Question question) {
        long answerCount = answerRepository.countByQuestionId(question.getId());
        String latestAnswerSnippet = answerRepository.findTopByQuestionIdOrderByCreatedAtDesc(question.getId())
                .map(answer -> truncate(answer.getContent(), 120))
                .orElse("");

        return new QuestionSummaryResponse(
                question.getId(),
                question.getTitle(),
                truncate(question.getDescription(), 140),
                question.getAuthorName(),
                question.getCreatedAt(),
                TagCodec.split(question.getTags()),
                answerCount,
                latestAnswerSnippet
        );
    }

    public QuestionDetailResponse toDetail(Question question) {
        long answerCount = answerRepository.countByQuestionId(question.getId());
        return new QuestionDetailResponse(
                question.getId(),
                question.getTitle(),
                question.getDescription(),
                question.getAuthorName(),
                question.getCreatedAt(),
                TagCodec.split(question.getTags()),
                answerCount
        );
    }

    public AnswerResponse toAnswer(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getQuestion().getId(),
                answer.getAuthorName(),
                answer.getContent(),
                answer.getCreatedAt(),
                answer.getLikes()
        );
    }

    private String truncate(String text, int maxLength) {
        if (text == null || text.isBlank()) {
            return "";
        }

        String safe = text.trim();
        if (safe.length() <= maxLength) {
            return safe;
        }

        return safe.substring(0, Math.max(maxLength - 1, 1)) + "…";
    }
}
