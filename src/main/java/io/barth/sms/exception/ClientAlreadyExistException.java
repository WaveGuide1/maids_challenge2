package io.barth.sms.exception;

public class ClientAlreadyExistException extends RuntimeException{

    public ClientAlreadyExistException(){}
    public ClientAlreadyExistException(String msg){
        super(msg);
    }
}
