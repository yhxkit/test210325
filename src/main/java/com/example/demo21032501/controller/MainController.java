package com.example.demo21032501.controller;

import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.model.SubmitBundle;
import com.example.demo21032501.service.MessageTemplateService;
import com.example.demo21032501.service.SubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@ResponseBody
public class MainController {

    private MessageTemplateService messageTemplateService;
    private SubmitService submitService;
    public MainController(MessageTemplateService messageTemplateService, SubmitService submitService){
        this.messageTemplateService = messageTemplateService;
        this.submitService = submitService;
    }


    @PostMapping("/submit")
    public ResponseEntity<ResultMessage> submit(@RequestBody SubmitBundle submitBundle){
        log.debug(submitBundle.getUserInfoList().size()+" cases to write to DB >> "+submitBundle);
        submitService.push(submitBundle); // 비동기 처리
        return new ResponseEntity<ResultMessage>(new ResultMessage<>(true, "전송을 시작합니다"), HttpStatus.OK);
    }



    @PostMapping("/check/{1}")
    public ResponseEntity<ResultMessage> check(@PathVariable("1") String userIdentifier){
        log.debug("LMS DB Writing identifier >> "+ userIdentifier);
        ResultMessage rm = submitService.checkProgress(userIdentifier);
        return new ResponseEntity<ResultMessage>(rm, HttpStatus.OK);
    }


    @PostMapping("/template")
    public ResponseEntity<ResultMessage> saveTemplate(@RequestBody MessageTemplate mt){
        log.debug("Save Template >> " + mt);
        ResultMessage rm = messageTemplateService.save(mt);
        return new ResponseEntity<ResultMessage>(rm, HttpStatus.OK);
    }


    @GetMapping("/template")
    public List<MessageTemplate> callTemplate(){
        return messageTemplateService.getAllStorageMessageTemplates();
    }



    @GetMapping("/template/{1}")
    public MessageTemplate getTemplate(@PathVariable("1") int idx){
        log.debug("Getting particular Template by idx >> "+idx);
        return messageTemplateService.getByIdx(idx);
    }


    @DeleteMapping("/template/{1}")
    public ResponseEntity<ResultMessage> delTemplate(@PathVariable("1") int idx){
        log.debug("Delete particular Template by idx >> "+idx);
        ResultMessage rm = messageTemplateService.deleteByIdx(idx);
        return new ResponseEntity<ResultMessage>(rm, HttpStatus.OK);
    }


    @GetMapping("/error")
    public void error(){
        log.debug("Error Handling from spring security");
    }

    @GetMapping("/sign-out")
    public void logout(){
        log.debug("sign out");
    }

}
