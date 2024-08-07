package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.mongo.AccountHistoryDocument;

public record AccountHistoryMongoDetailResponse(
    String id,
    String bhOutType,
    Long cardId,
    String bhMemo
) {
    public static AccountHistoryMongoDetailResponse from(AccountHistoryDocument accountHistory) {
        return new AccountHistoryMongoDetailResponse(
            accountHistory.getId(),
            accountHistory.getBhOutType(),
            accountHistory.getCardId(),
            accountHistory.getBhMemo()
        );
    }
}
