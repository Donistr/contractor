package org.example.contractor.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляющий ответ сервера при ошибочной ситуации
 */
@Data
@AllArgsConstructor
public class ErrorObject {

    private String error;

}
