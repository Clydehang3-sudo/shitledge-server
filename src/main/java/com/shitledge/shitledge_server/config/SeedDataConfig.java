package com.shitledge.shitledge_server.config;

import com.shitledge.shitledge_server.article.Article;
import com.shitledge.shitledge_server.article.ArticleRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedArticles(ArticleRepository articleRepository) {
        return args -> {
            if (articleRepository.count() > 0) {
                return;
            }

            List<Article> seeds = List.of(
                    new Article(
                            "A Longitudinal Study of Doomscrolling Before Bed",
                            "Dr. Mira Liang",
                            "mira@shitledge.com",
                            "We tracked 300 participants and confirmed that one last scroll never means one.",
                            "Introduction\\nThis paper investigates the ritual of saying good night while opening five more tabs.\\n\\nMethods\\nParticipants self-reported bedtime while screen-time logs quietly disagreed.\\n\\nFindings\\nNinety-two percent of subjects entered a productivity fantasy loop after midnight.\\n\\nConclusion\\nThe hypothesis stands: optimism peaks exactly when sleep quality collapses.",
                            LocalDate.of(2026, 3, 1)
                    ),
                    new Article(
                            "Peer Review of Group Chat Apologies",
                            "Prof. Naomi Park",
                            "naomi@shitledge.com",
                            "An analysis of the phrase 'sorry just saw this' across 12,000 delayed replies.",
                            "Abstract\\nWe classify apology templates by sincerity signature and emoji density.\\n\\nResults\\nThe highest trust score came from concise replies without punctuation fireworks.\\n\\nDiscussion\\nPeople forgive lateness when context is clear and theatrics are low.",
                            LocalDate.of(2026, 2, 18)
                    ),
                    new Article(
                            "Quantifying Monday Motivation Leakage",
                            "Ishaan Patel",
                            "ishaan@shitledge.com",
                            "A satirical framework for measuring how ambition exits the body between 9:00 and 9:17.",
                            "Background\\nMotivation was expected to remain stable through the first standup.\\n\\nObservation\\nCalendar collisions caused immediate morale evaporation.\\n\\nRecommendation\\nReplace three meetings with one agenda and two glasses of water.",
                            LocalDate.of(2026, 1, 30)
                    )
            );

            articleRepository.saveAll(seeds);
        };
    }
}
