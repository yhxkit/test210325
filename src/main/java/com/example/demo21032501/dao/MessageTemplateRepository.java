package com.example.demo21032501.dao;


import com.example.demo21032501.model.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Integer> {
    List<MessageTemplate> findAllByForStorageOrderByTemplateIdxDesc(boolean forStorage);
    MessageTemplate save(MessageTemplate mt);
}
