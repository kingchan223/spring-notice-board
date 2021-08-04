package com.community.config.auth;

import com.community.domain.entity.Member;
import com.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByLoginId(loginId);
        if(memberEntity != null){
            return new PrincipalDetails(memberEntity);
        }

//                .orElseThrow(()->{return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. 로그인 시도 아이디:"+loginId);});
        return null;
    }
}
