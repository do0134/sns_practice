package com.practice.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SNSApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public SNSApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }
    @Override
    public String getMessage(){
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s %s", errorCode.getMessage(),message);
    }

}
