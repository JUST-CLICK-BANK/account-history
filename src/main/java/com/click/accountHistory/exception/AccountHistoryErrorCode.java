package com.click.accountHistory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountHistoryErrorCode {
    NO_ACCOUNT_HISTORY("거래 내역이 없습니다.", HttpStatus.NO_CONTENT);

    private final String errorMessage;
    private final HttpStatus httpStatus;
}
