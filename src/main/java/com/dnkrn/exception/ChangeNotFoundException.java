package com.dnkrn.exception;

/**
 * Created by dinakaran on 9/18/17.
 */
public class ChangeNotFoundException extends  Exception {


    public ChangeNotFoundException(String msg){
        super("change cannot be given"+ msg);
    }
}
