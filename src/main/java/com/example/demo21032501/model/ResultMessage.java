package com.example.demo21032501.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultMessage<T> {

    boolean result;
    String message;
    T data;

    public ResultMessage(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
