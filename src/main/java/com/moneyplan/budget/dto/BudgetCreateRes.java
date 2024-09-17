package com.moneyplan.budget.dto;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.category.dto.CategoryRes;
import com.moneyplan.member.dto.MemberRes;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetCreateRes {
    private Long id;
    private MemberRes member;
    private CategoryRes category;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;

    public static BudgetCreateRes of(Budget budget) {
        return BudgetCreateRes.builder()
            .id(budget.getId())
            .member(MemberRes.of(budget.getMember()))
            .category(CategoryRes.of(budget.getCategory()))
            .startDate(budget.getStartDate())
            .endDate(budget.getEndDate())
            .amount(budget.getAmount())
            .build();
    }
}
