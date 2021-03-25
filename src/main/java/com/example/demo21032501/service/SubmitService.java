package com.example.demo21032501.service;


import com.example.demo21032501.model.LMS;
import com.example.demo21032501.model.MessageTemplate;
import com.example.demo21032501.model.ResultMessage;
import com.example.demo21032501.model.SubmitBundle;

public interface SubmitService {

    ResultMessage push(SubmitBundle sb);
    ResultMessage checkProgress(String senderIdentifier);
    ResultMessage send(LMS lms);

    void saveLmsLog(int total, MessageTemplate mt, String callback);
}
