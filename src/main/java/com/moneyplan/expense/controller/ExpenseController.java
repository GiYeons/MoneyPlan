package com.moneyplan.expense.controller;

import com.moneyplan.expense.dto.ExpenseCreateReq;
import com.moneyplan.expense.dto.ExpenseCreateRes;
import com.moneyplan.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Operation(summary = "지출 생성", description = "지출 기록을 생성합니다.")
    public ResponseEntity<ExpenseCreateRes> createExpense(@RequestBody ExpenseCreateReq req) {
        ExpenseCreateRes res = expenseService.createExpense(req);
        return ResponseEntity.ok(res);
    }

}
