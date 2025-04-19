package io.kanghyun.jwt_auth_prac.member.dao;

import io.kanghyun.jwt_auth_prac.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
