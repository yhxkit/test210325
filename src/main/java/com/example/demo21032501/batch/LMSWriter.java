package com.example.demo21032501.batch;

import com.example.demo21032501.dao.LMSRepository;
import com.example.demo21032501.model.LMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LMSWriter implements ItemWriter<LMS> {

    private LMSRepository lmsRepository;
    public LMSWriter(LMSRepository lmsRepository){
        this.lmsRepository = lmsRepository;
    }

    @Override
    public void write(List<? extends LMS> list) {
        list.forEach(this::doWrite);
    }

    private void doWrite(LMS lms){
        LMS lmss = lmsRepository.save(lms);
        log.info("전송됨 : {}", lmss);
    }
}
