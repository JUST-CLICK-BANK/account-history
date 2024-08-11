package com.click.accountHistory.domain.dto.response;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import com.click.accountHistory.domain.mongo.CategoryDocument;
import java.time.LocalDateTime;

public record AccountHistoryMongoResponse(
    Long historyId,
    LocalDateTime bhAt,
    String bhName,
    Long bhAmount,
    String bhStatus,
    Long bhBalance,
    CategoryDocument categoryId
) {
    public static AccountHistoryMongoResponse from(AccountHistoryDocument history) {
        return new AccountHistoryMongoResponse(
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
