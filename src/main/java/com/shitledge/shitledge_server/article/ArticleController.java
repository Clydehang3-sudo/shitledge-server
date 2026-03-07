package com.shitledge.shitledge_server.article;

import jakarta.validation.Valid;
import java.time.LocalDate;
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
@RequestMapping("/api/articles")
@CrossOrigin(originPatterns = {
        "http://localhost:5173",
        "http://localhost:5174",
        "https://*.vercel.app",
        "https://shitledge.com",
        "https://www.shitledge.com"
})
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ArticlePageResponse list(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 24);

        Pageable pageable = PageRequest.of(safePage, safeSize, resolveSort(sort));
        Page<Article> result;

        if (query == null || query.isBlank()) {
            result = articleRepository.findAll(pageable);
        } else {
            String keyword = query.trim();
            result = articleRepository
                    .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrSummaryContainingIgnoreCase(
                            keyword,
                            keyword,
                            keyword,
                            pageable
                    );
        }

        return new ArticlePageResponse(
                result.getContent().stream().map(ArticleSummaryResponse::from).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailResponse> detail(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ArticleDetailResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArticleDetailResponse> submit(@Valid @RequestBody SubmitArticleRequest request) {
        Article article = new Article(
                request.title().trim(),
                request.author().trim(),
                request.email().trim(),
                request.summary().trim(),
                request.body().trim(),
                LocalDate.now()
        );
        Article saved = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(ArticleDetailResponse.from(saved));
    }

    private Sort resolveSort(String sort) {
        return switch (sort) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "publishedDate", "id");
            case "title" -> Sort.by(Sort.Direction.ASC, "title");
            default -> Sort.by(Sort.Direction.DESC, "publishedDate", "id");
        };
    }
}
