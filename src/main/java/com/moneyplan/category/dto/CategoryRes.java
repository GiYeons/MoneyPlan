package com.moneyplan.category.dto;

import lombok.Getter;

@Getter
public class CategoryRes {

    private String name;

    public CategoryRes(String name) {
        this.name = name;
    }

}
