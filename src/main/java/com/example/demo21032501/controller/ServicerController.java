package com.example.demo21032501.controller;

import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.model.SimpleServicer;
import com.example.demo21032501.service.LmsServicerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@ResponseBody
@Controller
public class ServicerController {

    private LmsServicerService lmsServicerService;

    public ServicerController(LmsServicerService lmsServicerService){
        this.lmsServicerService = lmsServicerService;
    }

    @PostMapping("/service")
    public ResponseEntity<ResultMessage> saveNewServicer(@RequestBody SimpleServicer simpleServicer){
        return new ResponseEntity<ResultMessage>(lmsServicerService.save(simpleServicer), HttpStatus.OK);
    }

    @DeleteMapping("/service")
    public ResponseEntity<ResultMessage> deleteServicers(@RequestBody List<SimpleServicer> simpleServicers){
        return new ResponseEntity<ResultMessage>(lmsServicerService.delete(simpleServicers), HttpStatus.OK);

    }


}
