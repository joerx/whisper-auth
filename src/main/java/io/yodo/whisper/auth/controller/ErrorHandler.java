package io.yodo.whisper.auth.controller;

import io.yodo.whisper.commons.web.error.ErrorHandlerSupport;
import io.yodo.whisper.commons.web.error.ErrorResponse;
import io.yodo.whisper.commons.web.error.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ErrorHandlerSupport {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidRequestException ex) {
        return createResponse(ex, HttpStatus.BAD_REQUEST);
    }
}
