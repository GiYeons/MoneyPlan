package com.moneyplan.category.controller;

import com.moneyplan.category.dto.CategoryRes;
import com.moneyplan.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    @Operation(summary = "카테고리 목록", description = "카테고리 목록을 반환합니다.")
    public ResponseEntity<List<CategoryRes>> getCategories() {
        List<CategoryRes> res = categoryService.getCategories();

        return ResponseEntity.ok(res);
    }

}
