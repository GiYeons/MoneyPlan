package com.moneyplan.member.controller;

import com.moneyplan.member.dto.MemberReq;
import com.moneyplan.member.dto.MemberRes;
import com.moneyplan.member.repository.MemberRepository;
import com.moneyplan.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/")
    @Operation(summary = "회원가입", description = "계정명과 비밀번호로 회원가입을 합니다.")
    public ResponseEntity<MemberRes> register(@Valid @RequestBody MemberReq req) {
        MemberRes res = memberService.register(req);
        return ResponseEntity.ok(res);
    }


}
