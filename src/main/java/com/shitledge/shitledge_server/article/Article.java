package com.shitledge.shitledge_server.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, length = 120)
    private String author;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 600)
    private String summary;

    @Lob
    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDate publishedDate;

    protected Article() {
    }

    public Article(String title, String author, String email, String summary, String body, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.email = email;
        this.summary = summary;
        this.body = body;
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEmail() {
        return email;
    }

    public String getSummary() {
        return summary;
    }

    public String getBody() {
        return body;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }
}
