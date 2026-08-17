package com.example.demo.entity.vo;

import lombok.Data;

@Data
public class Page<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private T pageData;

    public Page(Integer currentPage, Integer pageSize, Long totalCount, T pageData) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageData = pageData;
    }

    public Page(T pageData) {
        this.pageData = pageData;
    }


}
