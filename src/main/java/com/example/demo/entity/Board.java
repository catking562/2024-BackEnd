package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Board() {

    }

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Board(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void update(String name) {
        this.name = name;
    }
}
