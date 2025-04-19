package io.kanghyun.jwt_auth_prac.member.app;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.kanghyun.jwt_auth_prac.global.config.JwtConfiguration;
import io.kanghyun.jwt_auth_prac.member.dao.RefreshTokenRepository;
import io.kanghyun.jwt_auth_prac.member.dto.TokenBody;
import io.kanghyun.jwt_auth_prac.member.domain.Member;
import io.kanghyun.jwt_auth_prac.member.domain.RefreshToken;
import io.kanghyun.jwt_auth_prac.member.dto.Role;
import io.kanghyun.jwt_auth_prac.member.dto.TokenPair;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfiguration jwtConfiguration;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenPair generateTokenPair(Member member) {
        String accessToken = issueAccessToken(member.getId(), member.getRole());
        String refreshToken = issueRefreshToken(member.getId(), member.getRole());

        RefreshToken token = new RefreshToken(refreshToken, member);
        refreshTokenRepository.save(token);

        return TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenBody parseJwt(String token) {
        Jws<Claims> parsed = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);

        String sub = parsed.getPayload().getSubject();
        String role = parsed.getPayload().get("role").toString();

        return new TokenBody(
                Long.parseLong(sub),
                Role.valueOf(role)
        );
    }

    public String issueAccessToken(Long id, Role role) {
        return issue(id, role, jwtConfiguration.getValidation().getAccess());
    }
    public String issueRefreshToken(Long id, Role role) {
        return issue(id, role, jwtConfiguration.getValidation().getRefresh());
    }
    public String issue(Long id, Role role, Long expTime) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expTime))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    private boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("token = {}", token);
            log.error("토큰이 이상해요...");
        } catch (IllegalArgumentException e) {
            log.error("token = {}", token);
            log.info("이상한 토큰이 검출되었습니다.");
        } catch (Exception e) {
            log.error("token = {}", token);
            log.info(";;");
        }
        return false;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecrets().getAppKey().getBytes());
    }

}
