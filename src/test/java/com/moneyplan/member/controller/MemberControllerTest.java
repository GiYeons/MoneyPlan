package com.moneyplan.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyplan.common.auth.JwtService;
import com.moneyplan.common.config.SecurityConfig;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.dto.MemberReq;
import com.moneyplan.member.dto.MemberRes;
import com.moneyplan.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 테스트")
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    private MemberReq memberReq;
    private MemberRes memberRes;

    @BeforeEach
    void setUp() {
        memberReq = MemberReq.builder()
            .account("MoneyPlan")
            .password("password1234@")
            .build();

        memberRes = MemberRes.builder()
            .id(1L)
            .account("MoneyPlan")
            .build();
    }

    @Test
    @DisplayName("회원가입: 성공(200)")
    @WithMockUser("user")
    void register_whenAllValid() throws Exception {
        // given
        when(memberService.register(any())).thenReturn(memberRes);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/v1/members/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberReq))
        );

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(memberRes.getId()))
            .andExpect(jsonPath("$.account").value(memberRes.getAccount()));
    }

    @Test
    @DisplayName("회원가입: 중복된 계정명인 경우(409)")
    @WithMockUser("user")
    void register_whenAccountConflict() throws Exception {
        // given
        when(memberService.register(any())).thenThrow(new BusinessException(ErrorCode.ACCOUNT_CONFLICT));

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/v1/members/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberReq))
        );

        // then
        resultActions.andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("이미 사용중인 계정입니다."));
    }
}