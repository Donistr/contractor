package org.example.contractor.exception;

/**
 * Исключение сообщающее о том, что страна не найдена
 */
public class CountryNotFoundException extends BaseException {

    public CountryNotFoundException(String message) {
        super(message);
    }

}
