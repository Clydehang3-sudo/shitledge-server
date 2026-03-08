package com.shitledge.shitledge_server.qa;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://*.vercel.app",
        "https://shitledge.com",
        "https://www.shitledge.com"
})
public class ExploreController {

    private final QuestionRepository questionRepository;
    private final QuestionViewFactory questionViewFactory;
    private final AnswerRepository answerRepository;

    public ExploreController(
            QuestionRepository questionRepository,
            QuestionViewFactory questionViewFactory,
            AnswerRepository answerRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionViewFactory = questionViewFactory;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/explore")
    public ExploreResponse explore() {
        List<Question> allQuestions = questionRepository.findAll();

        List<QuestionSummaryResponse> latest = allQuestions.stream()
                .sorted(Comparator.comparing(Question::getCreatedAt).reversed())
                .limit(6)
                .map(questionViewFactory::toSummary)
                .toList();

        List<QuestionSummaryResponse> hottest = allQuestions.stream()
                .sorted(
                        Comparator
                                .comparingLong((Question question) -> answerRepository.countByQuestionId(question.getId()))
                                .reversed()
                                .thenComparing(Question::getCreatedAt, Comparator.reverseOrder())
                )
                .limit(6)
                .map(questionViewFactory::toSummary)
                .toList();

        List<Question> shuffled = allQuestions.stream().collect(Collectors.toList());
        Collections.shuffle(shuffled, new Random());
        List<QuestionSummaryResponse> random = shuffled.stream()
                .limit(6)
                .map(questionViewFactory::toSummary)
                .toList();

        Map<String, Long> tagFrequency = new LinkedHashMap<>();
        allQuestions.forEach(question -> TagCodec.split(question.getTags())
                .forEach(tag -> tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0L) + 1L)));

        List<String> tags = tagFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(20)
                .toList();

        return new ExploreResponse(hottest, latest, random, tags);
    }

    @GetMapping("/tags")
    public List<String> tags() {
        return questionRepository.findAll().stream()
                .flatMap(question -> TagCodec.split(question.getTags()).stream())
                .distinct()
                .limit(40)
                .toList();
    }
}
