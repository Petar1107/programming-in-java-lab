package com.oss.lecture5.utils;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class ResponseMessage {
    private List<String> errors = new ArrayList<>();

    public ResponseMessage(String error) {
        this.errors.add(error);
    }
}
