package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.mongo.AccountHistoryDocument;

public record AccountHistoryMongoDetailResponse(
    String historyId,
    String bhOutType,
    Long cardId,
    String bhMemo
) {
    public static AccountHistoryMongoDetailResponse from(AccountHistoryDocument accountHistory) {
        return new AccountHistoryMongoDetailResponse(
            accountHistory.getHistoryId(),
            accountHistory.getBhOutType(),
            accountHistory.getCardId(),
            accountHistory.getBhMemo()
        );
    }
}
