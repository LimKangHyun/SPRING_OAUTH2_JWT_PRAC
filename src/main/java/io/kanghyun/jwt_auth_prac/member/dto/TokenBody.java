package io.kanghyun.jwt_auth_prac.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenBody {

    private Long memberId;
    private Role role;

}
