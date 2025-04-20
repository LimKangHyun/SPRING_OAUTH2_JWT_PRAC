package io.kanghyun.jwt_auth_prac.member.app;

import io.kanghyun.jwt_auth_prac.member.dao.MemberRepository;
import io.kanghyun.jwt_auth_prac.member.domain.Member;
import io.kanghyun.jwt_auth_prac.member.dto.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 역할_업데이트_테스트() {
        // 이메일은 실제 DB에 존재하는 사용자 이메일을 사용해야 함
        String email = "limkh06910691@gmail.com"; // ← 너의 구글 로그인 계정 이메일로 바꿔줘

        // 기존 멤버 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        assertThat(optionalMember).isPresent();

        Member member = optionalMember.get();

        // 기존 롤 확인
        Role beforeRole = member.getRole();
        System.out.println("Before Role: " + beforeRole);

        // 롤 변경
        member.updateRole(Role.ADMIN);
        memberRepository.save(member);

        // 다시 조회해서 변경됐는지 확인
        Member updatedMember = memberRepository.findByEmail(email).orElseThrow();
        Role afterRole = updatedMember.getRole();
        System.out.println("After Role: " + afterRole);

        // 검증
        assertThat(afterRole).isEqualTo(Role.ADMIN);
    }
}