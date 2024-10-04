package com.moneyplan.member.dto;

import com.moneyplan.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "AUTH_REQ_01 : 회원가입 요청 DTO")
public class MemberReq {

    @Schema(description = "계정명", example = "MoneyPlan")
    @NotBlank(message = "계정명은 필수 입력 값입니다.")
    @Size(min = 1, max = 50, message = "계정명은 1 ~ 50자까지 가능합니다.")
    private String account;

    @Schema(description = "비밀번호", example = "password1234@")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
        message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 하며, 8자 ~ 16자까지 가능합니다.")
    private String password;

    public Member toMember(String encodedPassword) {
        return Member.builder()
            .account(account)
            .password(encodedPassword)
            .build();
    }

}
