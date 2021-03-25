package com.example.demo21032501.dao;


import com.example.demo21032501.model.LmsServicer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LmsServicerRepository extends JpaRepository<LmsServicer, Integer> {
    LmsServicer findBySimpleServicerCallback(String callback);
    LmsServicer save(LmsServicer lmsServicer);
    List<LmsServicer> findAllByOrderByLmsServicerIdx();
}
