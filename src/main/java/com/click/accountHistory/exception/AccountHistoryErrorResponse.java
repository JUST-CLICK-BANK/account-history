package com.click.accountHistory.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountHistoryErrorResponse {

    private AccountHistoryErrorCode errorCode;
    private String errorMessage;
}
