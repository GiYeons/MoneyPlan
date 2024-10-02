package com.moneyplan.common.auth;

import com.moneyplan.member.domain.Member;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberAdapter extends User {

    private Member member;

    public MemberAdapter(Member member) {
        super(member.getAccount(), member.getPassword(), Collections.emptyList());
        this.member = member;
    }
}
