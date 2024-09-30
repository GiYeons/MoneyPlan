package com.moneyplan.expense.repository.custom;

import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.service.filter.ExpenseFilter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseCustomRepository {

    Page<Expense> findWithFilters(Pageable pageable, ExpenseFilter filter);


}
