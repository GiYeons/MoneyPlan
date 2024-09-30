package com.moneyplan.common.model;

import com.moneyplan.expense.domain.Expense;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class PageInfo {

    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;

    public PageInfo(Page<Expense> expensePage) {
        this.page = expensePage.getNumber() + 1;
        this.size = expensePage.getSize();
        this.totalElements = expensePage.getTotalElements();
        this.totalPages = expensePage.getTotalPages();
    }
}
