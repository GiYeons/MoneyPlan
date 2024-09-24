package com.moneyplan.expense.service;

import com.moneyplan.category.domain.Category;
import com.moneyplan.category.repository.CategoryRepository;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.dto.ExpenseCreateReq;
import com.moneyplan.expense.dto.ExpenseCreateRes;
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
    public ExpenseCreateRes createExpense(ExpenseCreateReq req) {

        // 회원가입/로그인 구현 전 임시 코드
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        // 임시 코드 끝

        Category category = categoryRepository.findById(req.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        Expense expense = req.toExpense(member, category);
        expenseRepository.save(expense);

        return ExpenseCreateRes.of(expense);
    }
}
