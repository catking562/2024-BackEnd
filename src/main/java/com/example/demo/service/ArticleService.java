package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.ArticleRepositoryJdbc;
import com.example.demo.repository.BoardRepositoryJdbc;
import com.example.demo.repository.MemberRepositoryJdbc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.ArticleCreateRequest;
import com.example.demo.controller.dto.response.ArticleResponse;
import com.example.demo.controller.dto.request.ArticleUpdateRequest;
import com.example.demo.entity.Article;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepositoryJdbc articleRepository;
    private final MemberRepositoryJdbc memberRepository;
    private final BoardRepositoryJdbc boardRepository;

    public ArticleService(
        ArticleRepositoryJdbc articleRepository,
        MemberRepositoryJdbc memberRepository,
        BoardRepositoryJdbc boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id);
        Member member = memberRepository.findById(article.getAuthorId());
        Board board = boardRepository.findById(article.getBoardId());
        return ArticleResponse.of(article, member, board);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(article -> {
                Member member = memberRepository.findById(article.getAuthorId());
                Board board = boardRepository.findById(article.getBoardId());
                return ArticleResponse.of(article, member, board);
            })
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = new Article(
            request.authorId(),
            request.boardId(),
            request.title(),
            request.description()
        );
        Article saved = articleRepository.insert(article);
        Member member = memberRepository.findById(saved.getAuthorId());
        Board board = boardRepository.findById(saved.getBoardId());
        return ArticleResponse.of(saved, member, board);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id);
        article.update(request.authorId(), request.boardId(), request.title(), request.description());
        Article updated = articleRepository.update(article);
        Member member = memberRepository.findById(updated.getAuthorId());
        Board board = boardRepository.findById(article.getBoardId());
        return ArticleResponse.of(article, member, board);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public Boolean isExistBoard(Long boardid) {
        return boardRepository.isExist(boardid);
    }

    public Boolean isExistUser(Long userid) {
        return memberRepository.isExist(userid);
    }
}
