package io.barth.sms.exception;

public class UserNotAuthenticatedException extends RuntimeException{

    public UserNotAuthenticatedException(){}

    public UserNotAuthenticatedException(String msg){super(msg);}
}
