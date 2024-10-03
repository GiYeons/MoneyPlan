package com.moneyplan.budget.service;

import com.moneyplan.budget.domain.Budget;
import com.moneyplan.budget.dto.BudgetCreateReq;
import com.moneyplan.budget.dto.BudgetRes;
import com.moneyplan.budget.dto.BudgetSuggestReq;
import com.moneyplan.budget.dto.BudgetSuggestRes;
import com.moneyplan.budget.repository.BudgetRepository;
import com.moneyplan.category.domain.Category;
import com.moneyplan.category.dto.CategoryRes;
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
    public List<BudgetRes> createBudget(Member member, BudgetCreateReq req) {

        if (member == null) {
            throw new BusinessException(ErrorCode.MISSING_AUTHENTICATION);
        }

        List<BudgetRes> res = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        Map<String, Integer> categoryBudgets = req.getCategoryBudgets();

        for (Category category : categories) {
            // 해당 카테고리에 대한 예산이 존재하지 않을 경우 기본값 0
            int amount = categoryBudgets.getOrDefault(category.getName(), 0);

            if (amount < 0) {
                throw new BusinessException(ErrorCode.INVALID_AMOUNT_EXCEPTION);
            }
            Budget budget = req.toBudget(member, category, amount);
            budgetRepository.save(budget);

            res.add(BudgetRes.of(budget));
        }
        return res;
    }

    @Transactional
    public List<BudgetSuggestRes> suggestBudget(BudgetSuggestReq req) {

        List<BudgetSuggestRes> res = new ArrayList<>();
        int totalAmount = req.getTotalAmount();

        Map<Long, Double> categoryPercentages = budgetRepository.calculateCategoryPercentages();

        int currentTotal = 0;

        for (Long categoryId : categoryPercentages.keySet()) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

            Double percentage = categoryPercentages.get(categoryId);

            if ("기타".equals(category.getName())) {
                continue;
            }

            // 10% 이하의 비율은 기타에 합산함
            int suggestedAmount = (percentage < 0.1) ? 0 : roundToNearestThousand(totalAmount * percentage);
            currentTotal += suggestedAmount;

            res.add(BudgetSuggestRes.builder()
                .category(CategoryRes.of(category))
                .amount(suggestedAmount)
                .build());
        }

        // 기타 카테고리 예산 계산
        int remainingAmount = totalAmount - currentTotal;

        if (remainingAmount > 0) {
            Category otherCategory = categoryRepository.findByName("기타")
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

            res.add(BudgetSuggestRes.builder()
                .category(CategoryRes.of(otherCategory))
                .amount(remainingAmount)
                .build());
        }
        return res;
    }

    private int roundToNearestThousand(double amount) {
        return (int) (Math.round(amount / 1000.0) * 1000);
    }
}