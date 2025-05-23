package io.kanghyun.jwt_auth_prac.member.domain;

import io.kanghyun.jwt_auth_prac.member.dto.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;

    private String provider;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Member(String username, String email, String provider) {
        this.username = username;
        this.email = email;
        this.provider = provider;
    }

    public void updateRole(Role role) {
        if(this.role != role) {
            this.role = role;
        }
    }
}