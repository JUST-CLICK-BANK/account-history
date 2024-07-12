package com.click.accountHistory.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class AccountHistoryException extends RuntimeException {

    private AccountHistoryErrorCode errorCode;
    private String errorMessage;

    public AccountHistoryException(AccountHistoryErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
