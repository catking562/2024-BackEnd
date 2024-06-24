package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "board_id")
    private Long boardId;
    private String title;
    private String content;
    @Column(name = "created_date")
    private LocalDateTime createdAt;
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    public Article() {
    }

    public Article(
        Long id,
        Long authorId,
        Long boardId,
        String title,
        String content
    ) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public Article(Long authorId, Long boardId, String title, String content) {
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(Long authorId, Long boardId, String title, String description) {
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = description;
        this.modifiedAt = LocalDateTime.now();
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
