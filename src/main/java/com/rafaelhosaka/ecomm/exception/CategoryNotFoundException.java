package com.rafaelhosaka.ecomm.exception;


import org.springframework.stereotype.Component;

@Component
public class CategoryNotFoundException extends RuntimeException{
    private String errorCode;
    private String errorMessage;

    public CategoryNotFoundException(){

    }

    public CategoryNotFoundException(String errorCode, String errorMessage){
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }


}
