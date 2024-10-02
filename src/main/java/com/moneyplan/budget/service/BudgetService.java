package com.moneyplan.budget.service;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.budget.dto.BudgetCreateReq;
import com.moneyplan.budget.dto.BudgetCreateRes;
import com.moneyplan.budget.repository.BudgetRepository;
import com.moneyplan.category.domain.Category;
import com.moneyplan.category.repository.CategoryRepository;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final BudgetRepository budgetRepository;

    @Transactional
    public List<BudgetCreateRes> createBudget(Member member, BudgetCreateReq req) {

        if (member == null) {
            throw new BusinessException(ErrorCode.MISSING_AUTHENTICATION);
        }
        List<BudgetCreateRes> res = new ArrayList<>();

        req.getCategoryBudgets().forEach((categoryName, amount) -> {
            if (amount < 0) {
                throw new BusinessException(ErrorCode.INVALID_AMOUNT_EXCEPTION);
            }
            Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

            Budget budget = req.toBudget(member, category, amount);
            budgetRepository.save(budget);

            res.add(BudgetCreateRes.of(budget));
        });

        return res;
    }
}
