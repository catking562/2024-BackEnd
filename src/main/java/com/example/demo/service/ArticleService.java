package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
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

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(
        ArticleRepository articleRepository,
        MemberRepository memberRepository,
        BoardRepository boardRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id);
        return ArticleResponse.of(article);
    }

    public List<ArticleResponse> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findAllByBoardId(boardId);
        return articles.stream()
            .map(ArticleResponse::of)//인텔리제이가 이렇게 바꿔주더라구요.
            .toList();
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = new Article(
            memberRepository.findById(request.authorId()),
            boardRepository.findById(request.boardId()),
            request.title(),
            request.description()
        );
        Article saved = articleRepository.insert(article);
        return ArticleResponse.of(saved);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleUpdateRequest request) {
        Member member = memberRepository.findById(request.authorId());
        Board board = boardRepository.findById(request.boardId());
        Article article = articleRepository.findById(id);
        article.update(member, board, request.title(), request.description());

        Article updated = articleRepository.update(article);
        return ArticleResponse.of(updated);
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
