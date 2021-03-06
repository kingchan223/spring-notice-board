package com.community.repository.member;

import com.community.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member save(Member member){
        em.persist(member);
        return em.find(Member.class, member.getId());
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

//    public List<Member> findByName(String name){
//        return em.createQuery("select m from Member m where m.name=:name").setParameter("name", name).getResultList();
//    }

    public Member findByLoginId(String loginId){
        List<Member> resultList = em.createQuery("select m from Member m where m.loginId=:loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
        if(!resultList.isEmpty()){
            return resultList.get(0);
        }
        return null;

    }

    public Member login(String loginId, String password) {
        Member member = findByLoginId(loginId);
//        System.out.println("MemberRepository login실행");
        if(loginId.equals(member.getLoginId()) && bCryptPasswordEncoder.matches(password,member.getPassword())){
            return member;
        }
        return null;
    }
}
