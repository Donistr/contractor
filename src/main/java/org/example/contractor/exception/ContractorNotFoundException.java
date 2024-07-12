package org.example.contractor.exception;

/**
 * Исключение сообщающее о том, что контрагент не найден
 */
public class ContractorNotFoundException extends BaseException {

    public ContractorNotFoundException(String message) {
        super(message);
    }

}
