package com.moneyplan.budget.repository.custom;

import static com.moneyplan.budget.domain.QBudget.budget;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BudgetCustomRepositoryImpl implements BudgetCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<Long, Double> calculateCategoryPercentages() {

        // 지난 30일간 총 예산 합계
        Integer totalAmountSubQuery = jpaQueryFactory
            .select(budget.amount.sum())
            .from(budget)
            .where(budget.startDate.goe(LocalDate.now().minusDays(30)))
            .fetchOne();

        if (totalAmountSubQuery == null || totalAmountSubQuery == 0) {
            return Map.of();
        }

        double totalAmount = totalAmountSubQuery.doubleValue();

        // 카테고리별 예산 비율 계산
        List<Tuple> categoryPercentages = jpaQueryFactory
            .select(budget.category.id, budget.amount.sum().doubleValue().divide(totalAmountSubQuery))
            .from(budget)
            .where(budget.startDate.goe(LocalDate.now().minusDays(30)))
            .groupBy(budget.category.id)
            .fetch();

        return categoryPercentages.stream().collect(Collectors.toMap(
            tuple -> tuple.get(budget.category.id),
            tuple -> tuple.get(1, Double.class)
        ));
    }
}
