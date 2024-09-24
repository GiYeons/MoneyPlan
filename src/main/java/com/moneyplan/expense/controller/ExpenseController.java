package com.moneyplan.expense.controller;

import com.moneyplan.expense.dto.ExpenseReq;
import com.moneyplan.expense.dto.ExpenseRes;
import com.moneyplan.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지출")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/")
    @Operation(summary = "지출 기록 생성", description = "지출 기록을 생성합니다.")
    public ResponseEntity<ExpenseRes> createExpense(@RequestBody ExpenseReq req) {
        ExpenseRes res = expenseService.createExpense(req);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @Operation(summary = "지출 기록 수정", description = "지출 기록을 수정합니다.")
    public ResponseEntity<ExpenseRes> updateExpense(@PathVariable Long id, @RequestBody ExpenseReq req) {
        ExpenseRes res = expenseService.updateExpense(id, req);
        return ResponseEntity.ok(res);
    }
}
