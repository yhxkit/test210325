package com.example.demo21032501.model;

import lombok.Data;

import java.util.List;

@Data
public class SubmitBundle {
    MessageTemplate messageTemplate;
    List<UserInfo> userInfoList;
    String senderIdentifier;

    String callback;
}
