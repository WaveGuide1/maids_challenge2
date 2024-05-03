package io.barth.sms.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){}

    public ProductNotFoundException(String msg){
        super(msg);
    }
}
