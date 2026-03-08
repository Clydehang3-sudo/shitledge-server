package com.shitledge.shitledge_server.qa;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(originPatterns = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://*.vercel.app",
        "https://shitledge.com",
        "https://www.shitledge.com"
})
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionViewFactory questionViewFactory;

    public QuestionController(
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            QuestionViewFactory questionViewFactory
    ) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.questionViewFactory = questionViewFactory;
    }

    @GetMapping
    public QuestionPageResponse list(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 40);

        Pageable pageable = PageRequest.of(safePage, safeSize, resolveSort(sort));
        Page<Question> result;

        if (query == null || query.isBlank()) {
            result = questionRepository.findAll(pageable);
        } else {
            String keyword = query.trim();
            result = questionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    keyword,
                    keyword,
                    pageable
            );
        }

        return new QuestionPageResponse(
                result.getContent().stream().map(questionViewFactory::toSummary).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailResponse> detail(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(questionViewFactory::toDetail)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuestionDetailResponse> create(@Valid @RequestBody CreateQuestionRequest request) {
        Question question = new Question(
                request.title().trim(),
                request.description().trim(),
                request.authorName().trim(),
                LocalDateTime.now(),
                TagCodec.join(TagCodec.split(request.tags()))
        );

        Question saved = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionViewFactory.toDetail(saved));
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<AnswerResponse>> answers(@PathVariable Long id) {
        if (!questionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<AnswerResponse> answers = answerRepository.findByQuestionIdOrderByCreatedAtDesc(id)
                .stream()
                .map(questionViewFactory::toAnswer)
                .toList();

        return ResponseEntity.ok(answers);
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<AnswerResponse> submitAnswer(
            @PathVariable Long id,
            @Valid @RequestBody CreateAnswerRequest request
    ) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        Answer answer = new Answer(
                question,
                request.authorName().trim(),
                request.content().trim(),
                LocalDateTime.now(),
                0
        );

        Answer saved = answerRepository.save(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionViewFactory.toAnswer(saved));
    }

    private Sort resolveSort(String sort) {
        return switch (sort) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt", "id");
            default -> Sort.by(Sort.Direction.DESC, "createdAt", "id");
        };
    }
}
