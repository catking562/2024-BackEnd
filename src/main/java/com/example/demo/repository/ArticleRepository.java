package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Article;

@Repository
public class ArticleRepository extends com.example.demo.repository.Repository<Article> {

    private final EntityManager entityManager;

    public ArticleRepository(EntityManager jdbcTemplate) {
        this.entityManager = jdbcTemplate;
    }

    /*@Override
    public List<Article> findAll() {
        return entityManager.createQuery("SELECT p FROM Article p", Article.class)
                .getResultList();
    }*/

    public List<Article> findAllByBoardId(Long boardId) {
        return entityManager.find(Board.class, boardId).getArticles();
    }

    public List<Article> findAllByMemberId(Long memberId) {
        return entityManager.find(Member.class, memberId).getArticles();
    }

    @Override
    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public Article insert(Article article) {
        entityManager.persist(article);
        return findById(article.getId());
    }

    @Override
    public Article update(Article article) {
        return entityManager.merge(article);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
