package com.moneyplan.expense.repository;

import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.repository.custom.ExpenseCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseCustomRepository {

}
