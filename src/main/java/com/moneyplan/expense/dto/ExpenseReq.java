package com.moneyplan.expense.dto;

import com.moneyplan.category.domain.Category;
import com.moneyplan.expense.domain.Expense;
import com.moneyplan.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ExpenseReq {

    private Long categoryId;
    private LocalDateTime spentAt;
    private int amount;
    private String memo;

    @Schema(defaultValue = "false")
    private Boolean isTotalExcluded;

    public Expense toExpense(Member member, Category category) {
        return Expense.builder()
            .member(member)
            .category(category)
            .spentAt(spentAt)
            .amount(amount)
            .memo(memo)
            .isTotalExcluded(isTotalExcluded)
            .build();
    }
}
