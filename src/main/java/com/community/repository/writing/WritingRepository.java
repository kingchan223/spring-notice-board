package com.community.repository.writing;


import com.community.domain.entity.Writing;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class WritingRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Writing writing){
        em.persist(writing);
    }

    public Writing findOne(Long id){
        return em.find(Writing.class, id);
    }

    public List<Writing> findAll(){
        return em.createQuery("select w from Writing w", Writing.class).getResultList();
    }

    public void delete(Long id){
        em.remove(findOne(id));
    }


}
