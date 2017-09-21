package com.dnkrn.exception;

/**
 * Exception thrown when no change found for the given value
 */
public class ChangeNotFoundException extends  Exception {


    public ChangeNotFoundException(String msg){
        super("change cannot be given for the input amount as "+ msg);
    }
}
