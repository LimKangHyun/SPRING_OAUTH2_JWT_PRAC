package io.kanghyun.jwt_auth_prac.member.app;

import io.kanghyun.jwt_auth_prac.member.dto.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Slf4j
public class MemberDetailsFactory {

    public static MemberDetails memberDetails(String provider, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        switch (provider.toUpperCase().trim()) {
            case "GOOGLE" -> {
                log.info("attributes : {}", attributes);
                return MemberDetails.builder()
                        .name(attributes.get("name").toString())
                        .email(attributes.get("email").toString())
                        .attributes(attributes)
                        .build();
            }
            case "NAVER" -> {
                Map<String, String> properties = (Map <String, String>)attributes.get("response");
                log.info("attributes : {}", attributes);
                return MemberDetails.builder()
                        .name(properties.get("name"))
                        .email(attributes.get("id") + "@naver.com")
                        .attributes(attributes)
                        .build();
            }
            case "KAKAO" -> {
                Map<String, String> properties = (Map <String, String>)attributes.get("properties");
                log.info("attributes : {}", attributes);
                return MemberDetails.builder()
                        .name(properties.get("nickname"))
                        .email(attributes.get("id").toString() + "@kakao.com")
                        .attributes(attributes)
                        .build();
            }
            default -> throw new IllegalStateException("지원하지 않는 제공자: " + provider);
        }
    }
}
