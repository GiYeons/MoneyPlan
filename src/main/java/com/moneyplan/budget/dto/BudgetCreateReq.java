package com.moneyplan.budget.dto;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.category.domain.Category;
import com.moneyplan.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;

@Getter
@Schema(description = "BUDGET_REQ_01 : 예산 설정 DTO")
public class BudgetCreateReq {

    @Schema(description = "시작일", example = "2024-10-02")
    @NotBlank(message = "시작일은 필수 입력 값입니다.")
    private LocalDate startDate;

    @Schema(description = "종료일", example = "2024-11-02")
    @NotBlank(message = "종료일은 필수 입력 값입니다.")
    private LocalDate endDate;

    @Schema(description = "카테고리별 예산", example = "{\"식비\": 200000, \"교통\": 55000}")
    @NotBlank(message = "카테고리별 예산은 필수 입력 값입니다.")
    private Map<String, Integer> categoryBudgets;

    public Budget toBudget(Member member, Category category, int amount) {
        return Budget.builder()
            .member(member)
            .category(category)
            .startDate(startDate)
            .endDate(endDate)
            .amount(amount)
            .build();
    }
}
