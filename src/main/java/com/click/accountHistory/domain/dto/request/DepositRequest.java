package com.click.accountHistory.domain.dto.request;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import java.time.LocalDateTime;

public record DepositRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String yourAccount, // 입금에서 내 통장에 표시되는 내용은 받는 분 표시라서 이게 필요한지 모르겠음
    String bhStatus,
    Long bhBalance,
    String bhReceive,
    Category categoryId
) {
    public AccountHistory toEntity() {
        return AccountHistory.builder()
            .bhAt(LocalDateTime.now())
            .bhName(bhName)
            .bhAmount(bhAmount)
            .myAccount(myAccount)
            .yourAccount(yourAccount)
            .bhStatus(bhStatus)
            .bhBalance(bhBalance)
            .bhOutType(null)
            .cardId(null)
            .bhReceive(bhReceive)
            .bhMemo(null)
            .categoryId(categoryId)
            .build();
    }
}
