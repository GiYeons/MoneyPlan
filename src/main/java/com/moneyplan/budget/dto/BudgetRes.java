package com.moneyplan.budget.dto;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.category.dto.CategoryRes;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetRes {
    private Long id;
    private CategoryRes category;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;

    public static BudgetRes of(Budget budget) {
        return BudgetRes.builder()
            .id(budget.getId())
            .category(CategoryRes.of(budget.getCategory()))
            .startDate(budget.getStartDate())
            .endDate(budget.getEndDate())
            .amount(budget.getAmount())
            .build();
    }
}
