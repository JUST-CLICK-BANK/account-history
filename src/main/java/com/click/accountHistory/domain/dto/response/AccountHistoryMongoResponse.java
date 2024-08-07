package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import java.time.LocalDateTime;

public record AccountHistoryMongoResponse(
    String id,
    LocalDateTime bhAt,
    String bhName,
    Long bhAmount,
    String bhStatus,
    Long bhBalance,
    String categoryName
) {
    public static AccountHistoryMongoResponse from(AccountHistoryDocument history) {
        return new AccountHistoryMongoResponse(
            history.getId(),
            history.getBhAt(),
            history.getBhName(),
            history.getBhAmount(),
            history.getBhStatus(),
            history.getBhBalance(),
            history.getCategoryName()
        );
    }
}
