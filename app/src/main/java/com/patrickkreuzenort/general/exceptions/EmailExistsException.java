package com.patrickkreuzenort.general.exceptions;

public class EmailExistsException extends RuntimeException  {
    public EmailExistsException(String s) {
        super(s);
    }
}
