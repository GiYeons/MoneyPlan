package com.moneyplan.budget.dto;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.category.domain.Category;
import com.moneyplan.member.domain.Member;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;

@Getter
public class BudgetCreateReq {

    private LocalDate startDate;
    private LocalDate endDate;
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
