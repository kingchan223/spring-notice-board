package com.community.repository.board;


import com.community.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class BoardRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    public List<Board> findAll(){
        return em.createQuery("select w from Board w", Board.class).getResultList();
    }

    public void delete(Long id){
        em.remove(findOne(id));
    }
}
