package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import java.util.List;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import java.util.UUID;

public interface AccountHistoryService {

    List<AccountHistoryResponse> getAllHistory(String account);

    AccountHistoryDetailResponse getHistoryDetail(Long historyId);

    void addDeposit(DepositRequest depositRequest);

    void addWithdraw(WithdrawRequest withdrawRequest);

    void updateHistoryMemo(Long historyId, String memo);
}
