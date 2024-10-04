package com.moneyplan.budget.controller;

import com.moneyplan.budget.dto.BudgetCreateReq;
import com.moneyplan.budget.dto.BudgetRes;
import com.moneyplan.budget.dto.BudgetSuggestReq;
import com.moneyplan.budget.dto.BudgetSuggestRes;
import com.moneyplan.budget.service.BudgetService;
import com.moneyplan.common.auth.model.AuthUser;
import com.moneyplan.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "예산 설정", description = "기간별 예산을 설정합니다.")
    public ResponseEntity<List<BudgetRes>> createBudget(@AuthUser Member member,
        @RequestBody BudgetCreateReq req) {
        List<BudgetRes> res = budgetService.createBudget(member, req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/suggest")
    @Operation(summary = "예산 설계", description = "카테고리 지정 없이 총액만 입력하면 카테고리별 예산을 자동 생성합니다.")
    public ResponseEntity<List<BudgetSuggestRes>> suggestBudget(@RequestBody BudgetSuggestReq req) {
        List<BudgetSuggestRes> res = budgetService.suggestBudget(req);
        return ResponseEntity.ok(res);
    }
}
