package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import java.time.LocalDateTime;

public record AccountHistoryDetailResponse(
    Long historyId,
    Integer bhOutType,
    Long cardId,
    String bhReceive,
    String bhMemo
) {
    public static AccountHistoryDetailResponse from(AccountHistory accountHistory) {
        return new AccountHistoryDetailResponse(
            accountHistory.getHistoryId(),
            accountHistory.getBhOutType(),
            accountHistory.getCardId(),
            accountHistory.getBhReceive(),
            accountHistory.getBhMemo()
        );
    }
}
