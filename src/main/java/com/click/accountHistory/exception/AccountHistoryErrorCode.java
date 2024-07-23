package com.click.accountHistory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountHistoryErrorCode {
    NO_ACCOUNT_HISTORY("거래 내역이 없습니다.", HttpStatus.NO_CONTENT),
    WRONG_ACCOUNT("계좌 정보가 없습니다.", HttpStatus.BAD_REQUEST),
    NO_CATEGORY("카테고리가 없습니다.", HttpStatus.BAD_REQUEST);

    private final String errorMessage;
    private final HttpStatus httpStatus;
}
