package com.click.accountHistory.domain.dto.request;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.type.TransactionType;
import java.time.LocalDateTime;

public record WithdrawRequest(
    String bhName,
    Long bhAmount,
    String myAccount,
    String yourAccount, // 입금에서 내 통장에 표시되는 내용은 받는 분 표시라서 이게 필요한지 모르겠음
    String bhStatus,
    Long bhBalance,
    Integer bhOutType,
    Long cardId,
    String bhReceive,
    Long categoryId
) {

    public AccountHistory toEntity(Category category) {
        return AccountHistory.builder()
            .bhAt(LocalDateTime.now())
            .bhName(bhName)
            .bhAmount(bhAmount)
            .myAccount(myAccount)
            .yourAccount(yourAccount)
            .bhStatus(bhStatus)
            .bhBalance(bhBalance)
            .bhOutType(TransactionType.fromValue(bhOutType))
            .cardId(cardId)
            .bhReceive(bhReceive)
            .bhMemo(null)
            .categoryId(category)
            .build();
    }
}
