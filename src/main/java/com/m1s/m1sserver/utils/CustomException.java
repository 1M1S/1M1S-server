package com.m1s.m1sserver.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
