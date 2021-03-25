package com.example.demo21032501.service;

import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DispatcherImpl implements Dispatcher {

    @Value("${com.estgames.lmstool.directive.username}")
    private String directiveUsername;


    @Override
    public String getValidCallback(String callback) {
        String checkStr = callback.replace("-", "");
        Long.parseLong(checkStr);
        return checkStr;
    }

    @Override
    public List<UserInfo> getValidPhoneNum(List<UserInfo> uiList) {
        List<UserInfo> ui = uiList;
        ui.stream().forEach(userInfo -> {
            String strNum = userInfo.getPhoneNum().replace("-", "");
            Long.parseLong(strNum); // 익셉션 뜨면 fail
            userInfo.setPhoneNum(strNum);
        });

        return duplicatedPhoneCheck(ui);
    }

    @Override
    public String buildParameters(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;

    }


    @Override
    public MessageTemplate setUsername(MessageTemplate mt, UserInfo ui) { // 지정어 > 유저명 치환
        MessageTemplate sendingOne = new MessageTemplate(mt.getTemplateName(), mt.getTemplate());
        sendingOne.setTemplateName(sendingOne.getTemplateName().replace(directiveUsername, ui.getName()));
        sendingOne.setTemplate(sendingOne.getTemplate().replace(directiveUsername, ui.getName()));
        return sendingOne;
    }


    @Override
    public  List<UserInfo> duplicatedPhoneCheck(List<UserInfo> list) {
        List<UserInfo> newlist = list.stream().filter( distinctByKey(p -> p.getPhoneNum()) ).collect(Collectors.toList());
        return newlist;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }



}
