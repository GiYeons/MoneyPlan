package com.moneyplan.expense.service;

import com.moneyplan.category.domain.Category;
import com.moneyplan.category.repository.CategoryRepository;
import com.moneyplan.common.exception.BusinessException;
import com.moneyplan.common.exception.ErrorCode;
import com.moneyplan.common.model.PageInfo;
import com.moneyplan.expense.domain.Expense;
import com.moneyplan.expense.dto.ExpenseReq;
import com.moneyplan.expense.dto.ExpenseRes;
import com.moneyplan.expense.dto.ExpensesRes;
import com.moneyplan.expense.dto.ExpensesRes.ExpenseCategoryTotal;
import com.moneyplan.expense.dto.ExpensesRes.ExpenseItem;
import com.moneyplan.expense.repository.ExpenseRepository;
import com.moneyplan.expense.repository.custom.ExpenseCustomRepository;
import com.moneyplan.expense.service.filter.ExpenseFilter;
import com.moneyplan.member.domain.Member;
import com.moneyplan.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ExpenseRes createExpense(Member member, ExpenseReq req) {

        if (member == null) {
            throw new BusinessException(ErrorCode.MISSING_AUTHENTICATION);
        }
        Category category = categoryRepository.findById(req.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        Expense expense = req.toExpense(member, category);
        expenseRepository.save(expense);

        return ExpenseRes.of(expense);
    }

    @Transactional
    public ExpenseRes updateExpense(Member member, Long id, ExpenseReq req) {

        Expense expense = verifyAccess(member, id);

        Category category = categoryRepository.findById(req.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        expense.update(category, req.getSpentAt(), req.getAmount(), req.getMemo(), req.getIsTotalExcluded());

        return ExpenseRes.of(expense);
    }

    @Transactional
    public void deleteExpense(Member member, Long id) {
        verifyAccess(member, id);
        expenseRepository.deleteById(id);
    }

    @Transactional
    public ExpenseRes getExpense(Member member, Long id) {
        Expense expense = verifyAccess(member, id);
        return ExpenseRes.of(expense);
    }

    @Transactional
    public ExpensesRes getExpenses(Member member, int page, int size, ExpenseFilter filter) {

        if (member == null) {
            throw new BusinessException(ErrorCode.MISSING_AUTHENTICATION);
        }
        filter.updateMemberId(member.getId());

        Pageable paging = PageRequest.of(page, size);
        Page<Expense> expensePage = expenseRepository.findWithFilters(paging, filter);

        List<ExpenseItem> expenseItems = expensePage.getContent().stream()
            .map(ExpenseItem::of)
            .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(expensePage);
        int totalAmount = expenseRepository.calculateTotalAmount(filter);
        List<ExpenseCategoryTotal> categoryTotals = expenseRepository.calculateCategoryTotals(filter);

        return ExpensesRes.builder()
            .expenses(expenseItems)
            .pageInfo(pageInfo)
            .totalAmount(totalAmount)
            .expenseCategoryTotals(categoryTotals)
            .build();
    }

    private Expense verifyAccess(Member member, Long expenseId) {
        if (member == null) {
            throw new BusinessException(ErrorCode.MISSING_AUTHENTICATION);
        }
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new BusinessException(ErrorCode.EXPENSE_NOT_FOUND));

        if (member.getId() != expense.getMember().getId()) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
        }
        return expense;
    }
}
