package com.example.demo.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

@Repository
public class MemberRepository extends com.example.demo.repository.Repository<Member> {

    private final EntityManager entitymanager;

    public MemberRepository(EntityManager entitymanager) {
        this.entitymanager = entitymanager;
    }

    @Override
    public List<Member> findAll() {
        return entitymanager.createQuery("SELECT p FROM Member p", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        return entitymanager.find(Member.class, id);
    }

    @Override
    public Member insert(Member member) {
        entitymanager.persist(member);
        return findById(member.getId());
    }

    @Override
    public Member update(Member member) {
        entitymanager.persist(member);
        return findById(member.getId());
    }

    @Override
    public void deleteById(Long id) {
        entitymanager.remove(findById(id));
    }

    public Boolean isExistEmail(Long notthis, String email) {
        try{
            entitymanager.createQuery(new StringBuilder("SELECT p FROM Member p WHERE NOT p.id = ")
                            .append(notthis).append(" AND p.email = '").append(email).append("'")
                            .toString(), Member.class).getSingleResult();
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean isExist(Long userid) {
        try{
            entitymanager.createQuery(new StringBuilder("SELECT p FROM Member p WHERE p.id = ")
                            .append(userid)
                            .toString(), Member.class);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
}
