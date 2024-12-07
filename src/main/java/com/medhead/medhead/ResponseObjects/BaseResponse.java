package com.medhead.medhead.ResponseObjects;

public class BaseResponse {

    private String exceptionMessage;
    private String newAuthenticationToken;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getNewAuthenticationToken() {
        return newAuthenticationToken;
    }

    public void setNewAuthenticationToken(String newAuthenticationToken) {
        this.newAuthenticationToken = newAuthenticationToken;
    }


    

   
    

}
