package com.example.demo21032501.batch;

import com.example.demo21032501.model.LMS;
import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.service.SubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LMSProcessor implements ItemProcessor<LMS, LMS> {

    private SubmitService submitService;
    public LMSProcessor (SubmitService submitService){
        this.submitService = submitService;
    }

    @Override
    public LMS process(LMS lms) {

        lms.setFlag(true);

        ResultMessage result = submitService.send(lms);
        log.info("LMS 전송 결과 >>> "+result.getData()); //문자 전송 api

        return lms;
    }
}
