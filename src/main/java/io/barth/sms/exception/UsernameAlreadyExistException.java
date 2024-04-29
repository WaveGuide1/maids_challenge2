package io.barth.sms.exception;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException(){}

    public UsernameAlreadyExistException(String msg){super(msg);}
}
