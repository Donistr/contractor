package org.example.contractor.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляет ответ сервера при штатной ситуации
 */
@Data
@AllArgsConstructor
public class ResponseObject {

    private String message;

}
