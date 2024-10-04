package com.moneyplan.member.service;

import com.moneyplan.common.auth.JwtService;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.dto.LoginMemberReq;
import com.moneyplan.member.dto.LoginMemberRes;
import com.moneyplan.member.dto.MemberReq;
import com.moneyplan.member.dto.MemberRes;
import com.moneyplan.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

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

    @Transactional
    public LoginMemberRes login(HttpServletResponse httpRes, LoginMemberReq req) {

        Member member = memberRepository.findByAccount(req.getAccount())
            .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!encoder.matches(req.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }
        String accessToken = jwtService.generateAccessToken(httpRes, member);
        String refreshToken = jwtService.generateRefreshToken(httpRes, member);

        return LoginMemberRes.of(member, accessToken, refreshToken);
    }
}
