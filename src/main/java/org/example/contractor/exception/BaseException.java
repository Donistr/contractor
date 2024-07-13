package org.example.contractor.exception;

/**
 * Базовый класс исключения для бизнес логики
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

}
