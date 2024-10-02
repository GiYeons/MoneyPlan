package com.moneyplan.expense.service.filter;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseFilter {

    Long memberId;

    LocalDateTime startDate;
    LocalDateTime endDate;
    Long categoryId;
    Integer minAmount;
    Integer maxAmount;

    @Builder
    public ExpenseFilter(Long memberId, LocalDateTime startDate, LocalDateTime endDate, Long categoryId,
        Integer minAmount, Integer maxAmount) {
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public void updateMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
