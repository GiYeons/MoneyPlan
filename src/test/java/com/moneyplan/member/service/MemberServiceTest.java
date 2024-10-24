package com.moneyplan.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moneyplan.common.auth.JwtService;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.dto.MemberReq;
import com.moneyplan.member.dto.MemberRes;
import com.moneyplan.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService 테스트")
class MemberServiceTest {

    @Spy
    private BCryptPasswordEncoder encoder;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private MemberReq memberReq;

    @BeforeEach
    void setUp() {
        memberReq = MemberReq.builder()
            .account("MoneyPlan")
            .password("password1234@")
            .build();

        member = memberReq.toMember(encoder.encode(memberReq.getPassword()));
    }

    @Test
    @DisplayName("회원가입 : 성공")
    void register_whenAllValid() {
        // given
        when(memberRepository.existsByAccount(any())).thenReturn(false);
        when(memberRepository.save(any())).thenReturn(member);

        // when
        MemberRes memberRes = memberService.register(memberReq);

        // then
        assertThat(memberRes.getId()).isEqualTo(member.getId());
        verify(memberRepository, times(1)).existsByAccount(any());
        verify(memberRepository, times(1)).save(any());
    }


}