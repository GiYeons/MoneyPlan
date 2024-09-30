package com.moneyplan.expense.controller;

import com.moneyplan.expense.dto.ExpenseReq;
import com.moneyplan.expense.dto.ExpenseRes;
import com.moneyplan.expense.dto.ExpensesRes;
import com.moneyplan.expense.service.ExpenseService;
import com.moneyplan.expense.service.filter.ExpenseFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping("/{id}")
    @Operation(summary = "지출 기록 삭제", description = "지출 기록을 삭제합니다.")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @GetMapping("/{id}")
    @Operation(summary = "지출 기록 조회 (상세)", description = "지출 기록(상세)을 단건 조회합니다.")
    public ResponseEntity<ExpenseRes> getExpense(@PathVariable Long id) {
        ExpenseRes res = expenseService.getExpense(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/")
    @Operation(summary = "지출 기록 조회 (목록)", description = "기간별 지출 기록을 조회합니다. 지출 합계 및 "
        + "카테고리별 지출 합계를 함께 반환합니다. 카테고리, 최소 금액, 최대 금액을 지정할 수 있습니다(선택).")
    public ResponseEntity<ExpensesRes> getExpenses(
        @Parameter(description = "시작일", example = "2024-09-01T00:00:00")
        @RequestParam @NotNull LocalDateTime startDate,
        @Parameter(description = "종료일", example = "2024-09-30T23:59:59")
        @RequestParam @NotNull LocalDateTime endDate,
        @Parameter(description = "카테고리 아이디 (1 ~ 11)")
        @RequestParam(required = false) Long categoryId,
        @Parameter(description = "최소 금액")
        @RequestParam(required = false, defaultValue = "0") Integer minAmount,
        @Parameter(description = "최대 금액")
        @RequestParam(required = false) Integer maxAmount,
        @Parameter(description = "페이지 번호")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기")
        @RequestParam(defaultValue = "20") int size
    ) {
        ExpenseFilter filter = ExpenseFilter.builder()
            .startDate(startDate)
            .endDate(endDate)
            .categoryId(categoryId)
            .minAmount(minAmount)
            .maxAmount(maxAmount)
            .build();

        ExpensesRes res = expenseService.getExpenses(page, size, filter);
        return ResponseEntity.ok(res);
    }
}
