package com.example.demo21032501.batch;

import com.example.demo21032501.dao.LMSRepository;
import com.example.demo21032501.model.LMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class LMSReader extends AbstractItemCountingItemStreamItemReader<LMS> {

    private Object lock = new Object();

    private LMSRepository lmsRepository;

    private int count;
    private List<LMS> list;

    public LMSReader(LMSRepository lmsRepository){
        this.lmsRepository = lmsRepository;
        setName("RequiredNameForAbstractItemCountingItemStreamItemReader");
    }

    @Override
    protected LMS doRead() throws Exception {
        log.debug("doRead From reader " + count);
        LMS lms = null;

        if(count < list.size()){
            lms = list.get(count);
            count++;
        }

        return lms;
    }

    @Override
    protected void doOpen() throws Exception {
        log.debug("doOpen from reader");
        count = 0;
        synchronized (lock) {
            this.list = lmsRepository.findTop500ByflagOrderByIdx(false);
            if (list == null) {
                this.list = Collections.emptyList();
            }
        }
    }

    @Override
    protected void doClose() throws Exception {
        synchronized (lock) {
            this.list.clear();
            this.list = null;
        }
    }


}
