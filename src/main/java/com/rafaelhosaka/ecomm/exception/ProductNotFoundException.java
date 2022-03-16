package com.rafaelhosaka.ecomm.exception;


import org.springframework.stereotype.Component;

@Component
public class ProductNotFoundException extends RuntimeException{
    private String errorCode;
    private String errorMessage;

    public ProductNotFoundException(){

    }

    public ProductNotFoundException(String errorCode,String errorMessage){
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
