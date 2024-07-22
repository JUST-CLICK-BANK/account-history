package com.click.accountHistory.controller;

import com.click.accountHistory.exception.AccountHistoryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountHistoryExceptionController {

    @ExceptionHandler(AccountHistoryException.class)
    public ResponseEntity<String> invalidValueException(AccountHistoryException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
            .body(e.getErrorCode().getErrorMessage());
    }
}
