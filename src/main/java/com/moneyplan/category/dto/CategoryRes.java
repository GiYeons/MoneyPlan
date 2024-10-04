package com.moneyplan.category.dto;

import com.moneyplan.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRes {

    private Long id;
    private String name;

    public static CategoryRes of(Category category) {
        return CategoryRes.builder()
            .id(category.getId())
            .name(category.getName())
            .build();
    }
}
