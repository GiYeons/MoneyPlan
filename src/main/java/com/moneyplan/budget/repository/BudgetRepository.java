package com.moneyplan.budget.repository;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.budget.repository.custom.BudgetCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>, BudgetCustomRepository {

}
