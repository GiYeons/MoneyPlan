package com.moneyplan.expense.repository.custom;

import static com.moneyplan.expense.domain.QExpense.expense;

import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.dto.ExpensesRes.ExpenseCategoryTotal;
import com.moneyplan.expense.dto.QExpensesRes_ExpenseCategoryTotal;
import com.moneyplan.expense.service.filter.ExpenseFilter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenseCustomRepositoryImpl implements ExpenseCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Expense> findWithFilters(Pageable pageable, ExpenseFilter filter) {
        List<Expense> expenses = jpaQueryFactory
            .selectFrom(expense)
            .where(buildConditions(filter))
            .orderBy(expense.spentAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
            .select(expense.count())
            .from(expense)
            .where(buildConditions(filter));

        return PageableExecutionUtils.getPage(expenses, pageable, countQuery::fetchOne);
    }

    @Override
    public int calculateTotalAmount(ExpenseFilter filter) {
        return jpaQueryFactory
            .select(expense.amount.sum().coalesce(0))
            .from(expense)
            .where(
                buildConditions(filter),
                expense.isTotalExcluded.eq(false)
            )
            .fetchOne();
    }

    @Override
    public List<ExpenseCategoryTotal> calculateCategoryTotals(ExpenseFilter filter) {
        return jpaQueryFactory
            .select(new QExpensesRes_ExpenseCategoryTotal(
                expense.category.name,
                expense.amount.sum().intValue().coalesce(0)
            ))
            .from(expense)
            .where(
                buildConditions(filter),
                expense.isTotalExcluded.eq(false)
                )
            .groupBy(expense.category.name)
            .fetch();
    }

    private BooleanExpression buildConditions(ExpenseFilter filter) {
        return expense.member.id.eq(filter.getMemberId())
            .and(expense.spentAt.goe(filter.getStartDate()))
            .and(expense.spentAt.loe(filter.getEndDate()))
            .and(categoryEq(filter.getCategoryId()))
            .and(amountGoe(filter.getMinAmount()))
            .and(amountLoe(filter.getMaxAmount()));
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null ? expense.category.id.eq(categoryId) : null;
    }

    private BooleanExpression amountGoe(Integer minAmount) {
        return minAmount != null || minAmount > 0 ? expense.amount.goe(minAmount) : null;
    }

    private BooleanExpression amountLoe(Integer maxAmount) {
        return maxAmount != null ? expense.amount.loe(maxAmount) : null;
    }
}
