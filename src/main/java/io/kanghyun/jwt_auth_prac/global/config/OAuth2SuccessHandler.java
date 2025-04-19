package io.kanghyun.jwt_auth_prac.global.config;

import io.kanghyun.jwt_auth_prac.member.app.JwtTokenProvider;
import io.kanghyun.jwt_auth_prac.member.app.MemberService;
import io.kanghyun.jwt_auth_prac.member.dao.MemberRepository;
import io.kanghyun.jwt_auth_prac.member.dto.MemberDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException  {
        {
            MemberDetails principal = (MemberDetails) authentication.getPrincipal();

            String token = jwtTokenProvider.issueAccessToken(principal.getId(), principal.getRole());
            log.info("token : {}", token);
        }
    }
}
