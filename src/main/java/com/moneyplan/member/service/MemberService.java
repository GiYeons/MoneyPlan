package com.moneyplan.member.service;

import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.dto.MemberReq;
import com.moneyplan.member.dto.MemberRes;
import com.moneyplan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public MemberRes register(MemberReq req) {

        if (memberRepository.existsByAccount(req.getAccount())) {
            throw new BusinessException(ErrorCode.ACCOUNT_CONFLICT);
        }

        String encodedPassword = encoder.encode(req.getPassword());
        Member member = req.toMember(encodedPassword);
        memberRepository.save(member);

        return MemberRes.of(member);
    }
}
