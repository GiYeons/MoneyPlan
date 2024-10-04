package com.moneyplan.member.dto;

import com.moneyplan.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginMemberRes {

    private Long id;
    private String account;
    private String accessToken;
    private String refreshToken;

    public static LoginMemberRes of(Member member, String accessToken, String refreshToken) {
        return LoginMemberRes.builder()
            .id(member.getId())
            .account(member.getAccount())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

}
