package com.moneyplan.member.dto;

import com.moneyplan.member.domain.Member;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRes {
    private Long id;
    private String account;

    public static MemberRes of(Member member) {
        return MemberRes.builder()
            .id(member.getId())
            .account(member.getAccount())
            .build();
    }
}
