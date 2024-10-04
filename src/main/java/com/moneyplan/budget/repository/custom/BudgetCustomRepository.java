package com.moneyplan.budget.repository.custom;

import java.util.Map;

public interface BudgetCustomRepository {

    /* 지난 30일간의 카테고리별 예산 비율 계산 */
    public Map<Long, Double> calculateCategoryPercentages();

}
