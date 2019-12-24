package com.myretail.product.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String returnMessage) {
        super(returnMessage);
    }

    public RecordNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RecordNotFoundException(Throwable throwable) {
        super(throwable);
    }

}
