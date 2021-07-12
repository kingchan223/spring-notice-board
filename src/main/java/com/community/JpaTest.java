package com.community;

import com.community.domain.entity.RoleType;
import com.community.domain.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaTest {
    public static void main(String[] args) {
        /*EntityManagerFactory를 만든다.*/
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*EntityManager만들기*/
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            //--비영속 상태--
            User user = new User();
            user.setId(100L);
            user.setEmail("koki@");
            user.setJoinedDate(LocalDateTime.now().toString());
            user.setPassword("1234");
            user.setRole(RoleType.USER);
            user.setUsername("이찬영");
//            // --영속 상태--  (아직 DB에 저장 X)
            em.persist(user);

            // 트랜잭션을
            tx.commit();

        }catch(Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();

    }
}
