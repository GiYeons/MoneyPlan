package com.moneyplan.expense.repository.custom;

import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.dto.ExpensesRes.ExpenseCategoryTotal;
import com.moneyplan.expense.service.filter.ExpenseFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseCustomRepository {

    /* 지출 목록 조회 */
    Page<Expense> findWithFilters(Pageable pageable, ExpenseFilter filter);

    /* 지출 합계 */
    int calculateTotalAmount(ExpenseFilter filter);
    
    /* 카테고리별 지출 합계 */
    List<ExpenseCategoryTotal> calculateCategoryTotals(ExpenseFilter filter);


}
