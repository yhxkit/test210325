package com.example.demo21032501.service.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PaginationImpl implements Pagination {
    @Override
    public PageRequest elementsByPage(int requiredPage, int elementsNumberPerOnePage, String sortBy) {
        requiredPage -= 1; //for pagination start from 0 not 1
        Sort sort = Sort.by( new Sort.Order(Sort.Direction.DESC, sortBy)) ;
        PageRequest pageRequest = PageRequest.of(requiredPage, elementsNumberPerOnePage, sort);
        return  pageRequest;
    }

    @Override
    public Sort.Order createNewSortOrder(String sortBy){
        return new Sort.Order(Sort.Direction.DESC, sortBy);
    }

    @Override
    public Sort createNewSort(Sort.Order... orders){
        System.out.println("엘립시스 체크"+orders);
        Sort sort = Sort.by(orders);
        return sort;
    }

    @Override
    public PageRequest elementsByPage(int requiredPage, int elementsNumberPerOnePage, Sort sort) {
        requiredPage -= 1; //for pagination start from 0 not 1
        PageRequest pageRequest = PageRequest.of(requiredPage, elementsNumberPerOnePage, sort);
        return  pageRequest;
    }


    @Override
    public Map<String, Object> pagers(int requiredPage, int pagerPerView, int totalPage) {
        requiredPage -= 1;

        Map<String, Object> pagerData = new HashMap<>();

        if(totalPage <= pagerPerView){
            pagerData.put("pager", IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList()));
            pagerData.put("prev", false);
            pagerData.put("next", false);
            return pagerData;
        }

        if(requiredPage < pagerPerView ){ // includes the case of "requiredPage == 0"
            pagerData.put("pager", IntStream.rangeClosed(1, pagerPerView).boxed().collect(Collectors.toList()));
            pagerData.put("prev", false);
            pagerData.put("next", true);
            return pagerData;
        }else{
            int startPage = (requiredPage/pagerPerView)*pagerPerView+1;
            int endPage = startPage+pagerPerView;
            boolean next = true;

            if(totalPage <= endPage){
                endPage = totalPage;
                next = false;
            }

            pagerData.put("next", next);
            pagerData.put("prev", true);
            pagerData.put("pager", IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList()));
            return pagerData;

        }
    }
}
