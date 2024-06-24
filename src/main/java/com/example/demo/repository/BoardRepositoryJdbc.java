package com.example.demo.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Board;

@Repository
public class BoardRepositoryJdbc extends com.example.demo.repository.Repository<Board> {

    private final EntityManager entityManager;

    public BoardRepositoryJdbc(EntityManager jdbcTemplate) {
        this.entityManager = jdbcTemplate;
    }

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("SELECT p FROM Board p", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        return entityManager.find(Board.class, id);
    }

    @Override
    public Board insert(Board board) {
        entityManager.persist(board);
        return findById(board.getId());
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Board update(Board board) {
        entityManager.persist(board);
        return findById(board.getId());
    }

    @Override
    public boolean isExist(Long id) {
        try{
            entityManager.createQuery(new StringBuilder("SELECT p FROM Board p WHERE p.id = ")
                            .append(id)
                            .toString(), Board.class);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
}
