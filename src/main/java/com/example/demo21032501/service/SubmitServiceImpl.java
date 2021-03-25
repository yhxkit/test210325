package com.example.demo21032501.service;

import com.example.demo21032501.dao.LMSRepository;
import com.example.demo21032501.dao.LmsLogRepository;
import com.example.demo21032501.dao.LmsServicerRepository;
import com.example.demo21032501.dao.MessageTemplateRepository;
import com.example.demo21032501.model.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SubmitServiceImpl implements SubmitService {


    @Value("${com.estgames.lmstool.api.url}")
    private String apiUrl;


    @Value("${com.estgames.lmstool.api.result-code.success}")
    private String apiSuccessResultCode;

    private static int count = 0;

    private Dispatcher dispatcher;
    private LMSRepository lmsRepository;
    private MessageTemplateRepository messageTemplateRepository;
    private LmsServicerRepository lmsServicerRepository;
    private LmsLogRepository lmsLogRepository;

    public SubmitServiceImpl(Dispatcher dispatcher, LMSRepository lmsRepository, MessageTemplateRepository messageTemplateRepository, LmsServicerRepository lmsServicerRepository, LmsLogRepository lmsLogRepository) {
        this.dispatcher = dispatcher;
        this.lmsRepository = lmsRepository;
        this.messageTemplateRepository = messageTemplateRepository;
        this.lmsServicerRepository = lmsServicerRepository;
        this.lmsLogRepository = lmsLogRepository;
    }

    @Override
    public ResultMessage send(LMS lms) {

        ResultMessage<StringBuffer> result = new ResultMessage<>();

        try {
            UserInfo ui = lms.getUser();
            MessageTemplate mt = lms.getMessageTemplate();

            MessageTemplate sendingOne = dispatcher.setUsername(mt, ui);
            Map<String, String> parameters = new HashMap<>();

            parameters.put("DestAddr", ui.getPhoneNum().trim()); //phoneNum에서 숫자만 남긴 값
            parameters.put("CallBack", lms.getCallback());
            parameters.put("subject", sendingOne.getTemplateName());
            parameters.put("msg", sendingOne.getTemplate());

            String params = dispatcher.buildParameters(parameters);
            String target = apiUrl + params;

            URL url = new URL(target);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);

            int status = con.getResponseCode();
            log.info("Result response code Status of sending LMS with API >> " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            log.info("LMS 전송 결과 >> " + content);
            if(content==null){
                content.append("LMS 전송 결과가 Null 입니다. 발송 내용 중 사용할 수 없는 특수기호나 문자가 들어갔을 수 있습니다.");
            }

            result.setMessage("LMS 전송 결과 >> " + content +"("+apiUrl+")");
            result.setResult(true);
            result.setData(content);

            JSONParser parser = new JSONParser();
            JSONObject resultJson = (JSONObject) parser.parse(content.toString());

            if (!resultJson.get("code").equals(apiSuccessResultCode)) {
                throw new RuntimeException("LMS 전송 실패 >> "+content);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public ResultMessage checkProgress(String senderIdentifier) {

        int total = 0;

        Optional<LMS> first = lmsRepository.findFirstBySenderIdentifier(senderIdentifier);
        if (first.isPresent()) {
            total = first.get().getTotalSending();
        }
        int sent = lmsRepository.countBySenderIdentifier(senderIdentifier);

        ResultMessage rm = new ResultMessage();

        if (sent < total) {
            rm.setResult(false);
        } else {
            rm.setResult(true);
        }

        double val = (double) sent / (double) total;

        rm.setData((int) (val * 100));
        rm.setMessage(sent + "/" + total + " 건 처리됨");


        if (total == 0) {
//            throw new RuntimeException("DB 출력 오류");
            throw new RuntimeException("오류가 발생했습니다. 올바른 정보가 입력되었는지 확인하세요.");
        }

        return rm;
    }


    @Override
    public void saveLmsLog(int total, MessageTemplate mt, String callback) {
        LmsLog lmslog = new LmsLog();

        mt.setForStorage(false);
        MessageTemplate standard = messageTemplateRepository.save(mt);

        lmslog.setSentMessageTemplate(standard);
        lmslog.setTotalSentCnt(total);

        LmsServicer lmsServicer= lmsServicerRepository.findBySimpleServicerCallback(callback);
        if(lmsServicer == null){
            throw new RuntimeException("!!!!! There is no servicer using this callback number : "+callback);
        }else{
            lmslog.setSimpleServicer(lmsServicer.getSimpleServicer());
        }

        lmsLogRepository.save(lmslog);

        log.info("LMS LOG 저장됨 >> "+ log);

    }

    @Async
    @Override
    public ResultMessage push(SubmitBundle sb) {

        count = 0;
        ResultMessage<StringBuffer> result = new ResultMessage();

        MessageTemplate mt = sb.getMessageTemplate();


        List<UserInfo> users = sb.getUserInfoList();
        int firstTotal = users.size();

        users = dispatcher.getValidPhoneNum(users); //get valid phoneNum excluding hyphens and get rid of duplicated phoneNum
        int total = users.size();

        String callback = dispatcher.getValidCallback(sb.getCallback());

        saveLmsLog(total, mt, callback); // 전송용 템플릿들을 저장하기 전에 기록 먼저 남김


        users.stream().forEach(user -> {
            MessageTemplate personalMessage = dispatcher.setUsername(mt, user);
            personalMessage.setForStorage(false);  //발신용 템플릿 별도 저장 //나중에 지정어 변경이 있으면 치환이 안되기 때문에 실제 발신용 템플릿에 각 유저 명 치환 완료한 상태로 저장
            messageTemplateRepository.save(personalMessage);
            lmsRepository.save(new LMS(user, personalMessage, sb.getSenderIdentifier(), total, callback));
            count++;
        });

        log.info("요청 받은 총 " + firstTotal + " 건 중, 중복번호를 제외한" + total + "건을 저장합니다. (저장 완료: " + count + "건 / 전송 식별자 : " + sb.getSenderIdentifier() + ")");
        result.setResult(true);

        return result;
    }


}

