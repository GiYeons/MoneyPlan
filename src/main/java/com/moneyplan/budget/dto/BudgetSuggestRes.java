package com.moneyplan.budget.dto;

import com.moneyplan.category.dto.CategoryRes;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetSuggestRes {
    private CategoryRes category;
    private int amount;
}
