package io.barth.sms.exception;

public class InvalidCredentialException extends RuntimeException{

    public InvalidCredentialException(String msg){
        super(msg);
    }
}
