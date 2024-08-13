package com.click.accountHistory.domain.dto.request;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.type.TransactionType;
import java.time.LocalDateTime;

public record DepositRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String bhStatus,
    Long bhBalance,
    Integer categoryId
) {

    public AccountHistory toEntity(Category category) {
        return AccountHistory.builder()
            .bhAt(LocalDateTime.now())
            .bhName(bhName)
            .bhAmount(bhAmount)
            .myAccount(myAccount)
            // .yourAccount(yourAccount)
            .bhStatus(bhStatus)
            .bhBalance(bhBalance)
            .bhOutType(TransactionType.DEPOSIT)
            .cardId(null)
            .bhMemo(null)
            .categoryId(category)
            .build();
    }
}
