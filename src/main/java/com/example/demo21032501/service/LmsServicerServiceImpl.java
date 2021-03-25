package com.example.demo21032501.service;

import com.example.demo21032501.dao.LmsServicerRepository;
import com.example.demo21032501.model.LmsServicer;
import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.model.SimpleServicer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LmsServicerServiceImpl implements LmsServicerService {

    private LmsServicerRepository lmsServicerRepository;
    private Dispatcher dispatcher;

    public LmsServicerServiceImpl(LmsServicerRepository lmsServicerRepository, Dispatcher dispatcher){
        this.lmsServicerRepository = lmsServicerRepository;
        this.dispatcher = dispatcher;
    }

    @Override
    public List<LmsServicer> getAllServicerList(){
        return lmsServicerRepository.findAllByOrderByLmsServicerIdx();
    }

    @Override
    public ResultMessage save(SimpleServicer simpleServicer) {

        String validCallback = dispatcher.getValidCallback(simpleServicer.getCallback());

        LmsServicer checkServicer = lmsServicerRepository.findBySimpleServicerCallback(validCallback);

        if(checkServicer != null){
            throw new RuntimeException("이미 등록된 번호입니다");
        }

        simpleServicer.setCallback(validCallback);
        LmsServicer newServicer = new LmsServicer(simpleServicer);

        log.info("새 서비스 저장 >> "+ lmsServicerRepository.save(newServicer));
        return new ResultMessage(true, "저장되었습니다");
    }

    @Override
    public ResultMessage delete(List<SimpleServicer> simpleServicerList) {

        simpleServicerList.stream().forEach(service ->{
            LmsServicer lmsServicer = lmsServicerRepository.findBySimpleServicerCallback(service.getCallback());
            log.info("서비스 삭제 >> "+lmsServicer);
            lmsServicerRepository.delete(lmsServicer);
        });
        return new ResultMessage(true, "삭제되었습니다");
    }
}
