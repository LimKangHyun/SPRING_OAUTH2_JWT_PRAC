package io.kanghyun.jwt_auth_prac.member.dto;

import io.kanghyun.jwt_auth_prac.member.domain.Member;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberDetails implements OAuth2User {

    private Long id;

    private String name;
    private String email;

    private Role role;

    public Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public static MemberDetails from(Member member, Map<String, Object> attributes) {
        return MemberDetails.builder()
                .id(member.getId())
                .name(member.getUsername())
                .email(member.getEmail())
                .role(member.getRole())
                .attributes(attributes)
                .build();
    }
}