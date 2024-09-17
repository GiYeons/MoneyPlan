package com.moneyplan.budget.controller;

import com.moneyplan.budget.dto.BudgetCreateReq;
import com.moneyplan.budget.dto.BudgetCreateRes;
import com.moneyplan.budget.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예산")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/")
    @Operation(summary = "예산 설정", description = "기간별 예산을 설정합니다. 예산당 카테고리는 필수로 지정해야 합니다.")
    public ResponseEntity<List<BudgetCreateRes>> createBudget(@RequestBody BudgetCreateReq req) {
        List<BudgetCreateRes> res = budgetService.createBudget(req);
        return ResponseEntity.ok(res);
    }

}
