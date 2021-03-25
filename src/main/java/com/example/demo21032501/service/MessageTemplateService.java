package com.example.demo21032501.service;

import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.ResultMessage;

import java.util.List;

public interface MessageTemplateService {
    ResultMessage save(MessageTemplate messageTemplate);
    MessageTemplate getByIdx(int idx);
    List<MessageTemplate> getAllStorageMessageTemplates();
    ResultMessage deleteByIdx(int idx);

}
