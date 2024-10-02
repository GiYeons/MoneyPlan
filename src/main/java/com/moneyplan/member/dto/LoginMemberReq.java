package com.moneyplan.member.dto;

import com.moneyplan.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "AUTH_REQ_02 : 로그인 요청 DTO")
public class LoginMemberReq {

    @Schema(description = "계정명", example = "MoneyPlan")
    @NotBlank(message = "계정명은 필수 입력 값입니다.")
    private String account;

    @Schema(description = "비밀번호", example = "password1234@")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
