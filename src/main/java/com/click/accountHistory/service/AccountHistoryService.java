package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import java.util.List;

public interface AccountHistoryService {

    List<AccountHistoryResponse> getAllHistory(String account);

    AccountHistoryDetailResponse getHistoryDetail(Long historyId);
}
