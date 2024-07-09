package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import java.time.LocalDateTime;

public record AccountHistoryDetailResponse(
    Long historyId,
    LocalDateTime bhAt,
    String bhName,
    Long bhAmount,
    String bhStatus,
    Integer bhOutType,
    Long cardId,
    String bhReceive,
    String bhMemo,
    Category categoryId
) {
    public static AccountHistoryDetailResponse from(AccountHistory accountHistory) {
        return new AccountHistoryDetailResponse(
            accountHistory.getHistoryId(),
            accountHistory.getBhAt(),
            accountHistory.getBhName(),
            accountHistory.getBhAmount(),
            accountHistory.getBhStatus(),
            accountHistory.getBhOutType(),
            accountHistory.getCardId(),
            accountHistory.getBhReceive(),
            accountHistory.getBhMemo(),
            accountHistory.getCategoryId()
        );
    }
}
