package com.example.demo21032501.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Map;

public interface Pagination {

    PageRequest elementsByPage(int requiredPage, int elementsNumberPerOnePage, String sortBy);

    Sort.Order createNewSortOrder(String sortBy);
    Sort createNewSort(Sort.Order... orders);
    PageRequest elementsByPage(int requiredPage, int elementsNumberPerOnePage, Sort sort);

    Map<String, Object> pagers(int requiredPage, int pagerPerView, int totalPage);
}
