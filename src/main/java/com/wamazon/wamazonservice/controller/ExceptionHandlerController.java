package com.wamazon.wamazonservice.controller;

import com.wamazon.wamazonservice.dto.ErrorNotification;
import com.wamazon.wamazonservice.exception.NotFoundException;
import com.wamazon.wamazonservice.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Контроллер для обработки ошибок, выбрасываемых в рамках проекта
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(ValidationException.class)
    public ErrorNotification handleValidationException(ValidationException e) {
        ErrorNotification errorNotification = new ErrorNotification();
        errorNotification.setErrorMessages(Collections.singletonList(e.getMessage()));
        return errorNotification;
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorNotification handleRuntimeException(RuntimeException e) {
        log.error("Произошла ошибка", e);

        ErrorNotification errorNotification = new ErrorNotification();
        errorNotification.setErrorMessages(
                Collections.singletonList("Произошла техническая ошибка. Обратитесь в службу технической поддержки."));
        return errorNotification;
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorNotification handleNotFoundException(NotFoundException e) {
        ErrorNotification errorNotification = new ErrorNotification();
        errorNotification.setErrorMessages(Collections.singletonList(e.getMessage()));
        return errorNotification;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorNotification handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        ErrorNotification errorNotification = new ErrorNotification();
        errorNotification.setErrorMessages(errors);
        return errorNotification;
    }
}
