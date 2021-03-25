package com.example.demo21032501.service;

import com.example.demo21032501.model.LmsServicer;
import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.model.SimpleServicer;

import java.util.List;

public interface LmsServicerService {
    List<LmsServicer> getAllServicerList();
    ResultMessage save(SimpleServicer simpleServicer);
    ResultMessage delete(List<SimpleServicer> simpleServicerList);
}
