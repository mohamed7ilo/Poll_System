package com.PollSystem.PollSystem.Controller;

public class Response {
    public Object data;
    public String errorMessage;


    public Response(Object daObject, String errorMessage){
        this.data = daObject;
        this.errorMessage = errorMessage;
    }
}
