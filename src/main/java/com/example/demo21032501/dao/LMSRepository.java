package com.example.demo21032501.dao;

import com.example.demo21032501.model.LMS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LMSRepository extends CrudRepository<LMS, Integer> {
    List<LMS> findTop500ByflagOrderByIdx(boolean flag);
    List<LMS> findAllByflagOrderByIdx(boolean flag);
    LMS findFirstByFlagOrderByIdxAsc(boolean flag);
    int countBySenderIdentifier(String senderIdentifier);
    Optional<LMS> findFirstBySenderIdentifier(String senderIdentifier);




}
