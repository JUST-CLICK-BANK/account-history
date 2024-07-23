package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.type.TransactionType;

public record AccountHistoryDetailResponse(
    Long historyId,
    String bhOutType,
    Long cardId,
    String bhMemo
) {

    public static AccountHistoryDetailResponse from(AccountHistory accountHistory) {
        return new AccountHistoryDetailResponse(
            accountHistory.getHistoryId(),
            accountHistory.getBhOutType().getName(),
            accountHistory.getCardId(),
            accountHistory.getBhMemo()
        );
    }
}
