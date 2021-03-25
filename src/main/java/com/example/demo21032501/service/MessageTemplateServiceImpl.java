package com.example.demo21032501.service;

import com.example.demo21032501.dao.MessageTemplateRepository;
import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.ResultMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private MessageTemplateRepository messageTemplateRepository;

    public MessageTemplateServiceImpl(MessageTemplateRepository messageTemplateRepository) {
        this.messageTemplateRepository = messageTemplateRepository;
    }

    @Override
    public ResultMessage save(MessageTemplate messageTemplate) {
        messageTemplateRepository.save(messageTemplate);
        return new ResultMessage<>(true, "저장되었습니다");
    }

    @Override
    public MessageTemplate getByIdx(int idx) {
        Optional<MessageTemplate> message = messageTemplateRepository.findById(idx);
        if (message.isPresent()) {
            return message.get();
        } else {
            return new MessageTemplate();
        }
    }

    @Override
    public List<MessageTemplate> getAllStorageMessageTemplates() {
        return messageTemplateRepository.findAllByForStorageOrderByTemplateIdxDesc(true);
    }

    @Override
    public ResultMessage deleteByIdx(int idx) {
        Optional<MessageTemplate> mt = messageTemplateRepository.findById(idx);
        if(mt.isPresent() && mt.get().isForStorage() == true){ // 저장용 템플릿만 삭제
            messageTemplateRepository.delete(mt.get());
        }
        return new ResultMessage(true, "삭제되었습니다");
    }
}
