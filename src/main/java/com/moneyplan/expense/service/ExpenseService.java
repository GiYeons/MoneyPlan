package com.moneyplan.expense.service;

import com.moneyplan.category.domain.Category;
import com.moneyplan.category.repository.CategoryRepository;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.dto.ExpenseReq;
import com.moneyplan.expense.dto.ExpenseRes;
import com.moneyplan.expense.repository.ExpenseRepository;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ExpenseRes createExpense(ExpenseReq req) {

        // 회원가입/로그인 구현 전 임시 코드
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        // 임시 코드 끝

        Category category = categoryRepository.findById(req.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        Expense expense = req.toExpense(member, category);
        expenseRepository.save(expense);

        return ExpenseRes.of(expense);
    }

    @Transactional
    public ExpenseRes updateExpense(Long id, ExpenseReq req) {

        // 회원가입/로그인 구현 전 임시 코드
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        // 임시 코드 끝

        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.EXPENSE_NOT_FOUND));

        Category category = categoryRepository.findById(req.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        expense.update(category, req.getSpentAt(), req.getAmount(), req.getMemo(), req.isTotalExcluded());

        return ExpenseRes.of(expense);
    }
}
