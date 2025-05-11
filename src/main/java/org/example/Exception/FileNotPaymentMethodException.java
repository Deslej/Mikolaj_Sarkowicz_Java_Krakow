package org.example.Exception;

public class FileNotPaymentMethodException extends RuntimeException{
    public FileNotPaymentMethodException(String message) {
        super(message);
    }
}
