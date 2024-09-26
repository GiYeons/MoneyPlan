package com.moneyplan.expense.dto;

import com.moneyplan.category.dto.CategoryRes;
import com.moneyplan.expense.domain.Expense;
import com.moneyplan.member.dto.MemberRes;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpenseRes {

    private Long id;
    private MemberRes member;
    private CategoryRes category;
    private LocalDateTime spentAt;
    private int amount;
    private String memo;
    private Boolean isTotalExcluded;

    public static ExpenseRes of(Expense expense) {
        return ExpenseRes.builder()
            .id(expense.getId())
            .member(MemberRes.of(expense.getMember()))
            .category(CategoryRes.of(expense.getCategory()))
            .spentAt(expense.getSpentAt())
            .amount(expense.getAmount())
            .memo(expense.getMemo())
            .isTotalExcluded(expense.getIsTotalExcluded())
            .build();
    }

}
