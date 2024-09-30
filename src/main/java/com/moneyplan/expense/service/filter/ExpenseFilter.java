package com.moneyplan.expense.service.filter;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseFilter {

    LocalDateTime startDate;
    LocalDateTime endDate;
    Long categoryId;
    Integer minAmount;
    Integer maxAmount;

    @Builder
    public ExpenseFilter(LocalDateTime startDate, LocalDateTime endDate, Long categoryId,
        Integer minAmount, Integer maxAmount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
