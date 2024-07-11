package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import java.time.LocalDateTime;

public record AccountHistoryResponse(
    Long historyId,
    LocalDateTime bhAt,
    String bhName,
    Long bhAmount,
    String bhStatus,
    Long bhBalance,
    Category categoryId
) {
    public static AccountHistoryResponse from(AccountHistory history) {
        return new AccountHistoryResponse(
            history.getHistoryId(),
            history.getBhAt(),
            history.getBhName(),
            history.getBhAmount(),
            history.getBhStatus(),
            history.getBhBalance(),
            history.getCategoryId()
        );
    }
}
