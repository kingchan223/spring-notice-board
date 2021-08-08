package com.community.config.oauth;

import com.community.config.auth.PrincipalDetails;
import com.community.config.auth.provider.GoogleUserInfo;
import com.community.config.auth.provider.OAuth2UserInfo;
import com.community.domain.entity.Member;
import com.community.domain.entity.RoleType;
import com.community.repository.member.MemberRepository;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2User oauth2User =  super.loadUser(userRequest);
        OAuth2UserInfo oauth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oauth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        }
        else{
            throw new OAuth2AuthenticationException("구글, 페이스북, 네이버 로그인만을 지원합니다.");
        }

        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String loginId = provider+"_"+providerId;
        String name = oauth2UserInfo.getName();
        String email = oauth2UserInfo.getEmail();
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        RoleType role = RoleType.ROLE_USER;
        Member member = memberRepository.findByLoginId(loginId);
        if(member == null){//OAuth로그인을 최초로 했음
            System.out.println("오아스 최초, 회원가입 진행");
            Member oAuthMember = Member.createOAuthMember(name, loginId, email, password, role, provider, providerId);
            memberService.addUserBasic(oAuthMember);
            return new PrincipalDetails(oAuthMember, oauth2User.getAttributes());
        }
        else{//이미 OAuth로 로그인한적 있음
            System.out.println("오아스 한적있음, 있던 회원 반환");
            return new PrincipalDetails(member);
        }

    }
}
