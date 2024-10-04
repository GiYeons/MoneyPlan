package com.moneyplan.category_average_budget.repository;

import com.moneyplan.category_average_budget.domain.CategoryAverageBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAverageBudgetRepository extends JpaRepository<CategoryAverageBudget, Long> {

}
