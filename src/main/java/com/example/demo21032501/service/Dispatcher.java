package com.example.demo21032501.service;



import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.UserInfo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface  Dispatcher {
    List<UserInfo> getValidPhoneNum(List<UserInfo> ui);
    MessageTemplate setUsername(MessageTemplate mt, UserInfo ui);
    String buildParameters(Map<String, String> params)  throws UnsupportedEncodingException;
    List<UserInfo> duplicatedPhoneCheck(List<UserInfo> ui);

    String getValidCallback(String callback);

}
