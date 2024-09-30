package com.moneyplan.expense.dto;

import com.moneyplan.category.dto.CategoryRes;
import com.moneyplan.common.model.PageInfo;
import com.moneyplan.expense.domain.Expense;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpensesRes {

    private List<ExpenseItem> expenses;
    private PageInfo pageInfo;
    private int totalAmount;
    private List<ExpenseCategoryTotal> expenseCategoryTotals;

    public ExpensesRes(List<ExpenseItem> expenses, PageInfo pageInfo, int totalAmount,
        List<ExpenseCategoryTotal> expenseCategoryTotals) {
        this.expenses = expenses;
        this.pageInfo = pageInfo;
        this.totalAmount = totalAmount;
        this.expenseCategoryTotals = expenseCategoryTotals;
    }

    @Getter
    @Builder
    public static class ExpenseItem {

        private Long id;
        private CategoryRes category;
        private LocalDateTime spentAt;
        private int amount;

        public static ExpenseItem of(Expense expense) {
            return ExpenseItem.builder()
                .id(expense.getId())
                .category(CategoryRes.of(expense.getCategory()))
                .spentAt(expense.getSpentAt())
                .amount(expense.getAmount())
                .build();
        }
    }

    @Getter
    @Builder
    public static class ExpenseCategoryTotal {

        private String name;
        private int totalAmount;

        public ExpenseCategoryTotal(String name, int totalAmount) {
            this.name = name;
            this.totalAmount = totalAmount;
        }
    }
}
