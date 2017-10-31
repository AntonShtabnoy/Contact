package com.shtabnoy.contact.model.exception;

public class RequestAttributeException extends Exception {


    public RequestAttributeException(){
    }

    public RequestAttributeException(String message, Throwable exception){
        super(message, exception);
    }

    public RequestAttributeException(String message){
        super(message);
    }

    public RequestAttributeException(Throwable exception){
        super(exception);
    }
}
