package io.kanghyun.jwt_auth_prac.member.app;

import io.kanghyun.jwt_auth_prac.member.dao.MemberRepository;
import io.kanghyun.jwt_auth_prac.member.domain.Member;
import io.kanghyun.jwt_auth_prac.member.dto.MemberDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerId = userRequest.getClientRegistration().getRegistrationId();

        MemberDetails memberDetails = MemberDetailsFactory.memberDetails(providerId, oAuth2User);

        Optional<Member> optionalMember = memberRepository.findByEmail(memberDetails.getEmail());

        Member findMember = optionalMember.orElseGet(
                () -> {
                    Member member = Member.builder()
                            .username(memberDetails.getName())
                            .email(memberDetails.getEmail())
                            .provider(providerId)
                            .build();
                    return memberRepository.save(member);
                }
        );
        if (findMember.getProvider().equals(providerId)) {
            return MemberDetails.builder()
                    .id(findMember.getId())
                    .name(findMember.getUsername())
                    .email(findMember.getEmail())
                    .role(findMember.getRole())
                    .attributes(memberDetails.getAttributes())
                    .build();
        } else {
            throw new IllegalStateException("이미 다른 제공자로 가입한 이력이 있습니다.");
        }
    }

}