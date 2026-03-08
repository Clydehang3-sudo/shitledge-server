package com.shitledge.shitledge_server.qa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 80)
    private String authorName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 320)
    private String tags;

    protected Question() {
    }

    public Question(String title, String description, String authorName, LocalDateTime createdAt, String tags) {
        this.title = title;
        this.description = description;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTags() {
        return tags;
    }
}
